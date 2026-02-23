package entelect.training.incubator.spring.booking.api;

import entelect.training.incubator.spring.booking.error.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerApiClient {

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8201")        .defaultHeaders(headers ->
            headers.setBasicAuth("user", "the_cake") // or admin credentials
            ).build();

    public Mono<Void> verifyCustomerExists(String customerId) {
        return webClient.get()
                .uri("/customers/{id}", customerId)

                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        r -> Mono.error(new CustomerNotFoundException(customerId))
                )
//                .onStatus(
//                        HttpStatus::is5xxServerError,
//                        r -> Mono.error(new CustomerServiceUnavailableException())
//                )
                .toBodilessEntity()
                .then();
    }
}
