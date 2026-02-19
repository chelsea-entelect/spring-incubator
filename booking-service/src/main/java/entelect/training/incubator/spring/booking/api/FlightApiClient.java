package entelect.training.incubator.spring.booking.api;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FlightApiClient {

    private final WebClient webClient = WebClient.create("http://localhost:8202");


    @GetMapping("/{id}")
    public void verifyFlightExists(String flightId) {
         webClient.get()
                .uri("/flights/{id}", flightId)
                .retrieve()
                .toBodilessEntity().block();
    }

}
