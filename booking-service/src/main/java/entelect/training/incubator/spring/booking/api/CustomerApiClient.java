package entelect.training.incubator.spring.booking.api;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomerApiClient {

    private final WebClient webClient = WebClient.create("http://localhost:8201");

    @GetMapping("/{id}")
    public void verifyCustomerExists(String customerId) {
         webClient.get()
                .uri("/customers/{id}", customerId)
                .retrieve().toBodilessEntity().block();
    }
}
