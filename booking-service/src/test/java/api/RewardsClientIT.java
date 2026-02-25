package api;

import com.baeldung.springsoap.client.gen.CaptureRewardsResponse;
import entelect.training.incubator.spring.booking.BookingsServiceApplication;
import entelect.training.incubator.spring.booking.api.RewardsApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookingsServiceApplication.class)

public class RewardsClientIT {

    @Container
    static GenericContainer<?> loyalty =
            new GenericContainer<>("loyalty-service:latest")
                    .withExposedPorts(8208)
                    .waitingFor(
                            Wait.forHttp("/ws?wsdl")
                                    .forPort(8208)
                                    .forStatusCodeMatching(code -> code == 405 || code == 200)
                                    .withStartupTimeout(Duration.ofSeconds(30))
                    );

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {
        registry.add(
                "rewards.endpoint",
                () -> "http://" +
                        loyalty.getHost() +
                        ":" +
                        loyalty.getMappedPort(8208) +
                        "/ws"
        );
    }
    @Autowired
    RewardsApiClient client;

    @Test
    public void test(){
        assertNotNull(client);
        CaptureRewardsResponse captureRewardsResponse =  client.captureRewards("A0924531", BigDecimal.ONE).block();

        assertEquals(captureRewardsResponse.getBalance(), BigDecimal.ONE);
    }
}
