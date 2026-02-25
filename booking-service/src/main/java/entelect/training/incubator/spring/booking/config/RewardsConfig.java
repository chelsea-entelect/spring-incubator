package entelect.training.incubator.spring.booking.config;

import entelect.training.incubator.spring.booking.api.RewardsApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@ConditionalOnProperty(
        name = "rewards.endpoint",
        matchIfMissing = false
)
public class RewardsConfig {

    @Value("${rewards.endpoint}")
    private String rewardsEndpoint;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.baeldung.springsoap.client.gen");
        return marshaller;
    }

    @Bean
    public RewardsApiClient rewardsClient(Jaxb2Marshaller marshaller) {
        RewardsApiClient client = new RewardsApiClient();
        client.setDefaultUri(rewardsEndpoint); // ✅ dynamic, container-safe
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
