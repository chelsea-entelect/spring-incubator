package entelect.training.incubator.spring.booking.api;

import entelect.training.incubator.spring.customer.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import entelect.training.incubator.spring.flight.model.Flight;

@Component
public class FlightApiClient {

    private final WebClient webClient = WebClient.create("http://localhost:8202");


    @GetMapping("/{id}")
    public Flight getFlightsById(String flightId) {
        return webClient.get()
                .uri("/flights/{id}", flightId)
                .retrieve()
                .bodyToMono(Flight.class).block();
    }

}
