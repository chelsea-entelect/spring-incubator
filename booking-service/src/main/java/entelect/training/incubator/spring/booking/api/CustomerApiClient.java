package entelect.training.incubator.spring.booking.api;

import entelect.training.incubator.spring.customer.model.Customer;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerApiClient {

    private final WebClient webClient = WebClient.create("http://localhost:8201");

    @GetMapping("/{id}")
    public Customer getCustomerById(String customerId) {
        return webClient.get()
                .uri("/customers/{id}", customerId)
                .retrieve().bodyToMono(Customer.class).block();
    }
}
