//package entelect.training.incubator.spring.booking.legacy.api;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Component
//public class FlightApiClient {
//
//    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8202")        .defaultHeaders(headers ->
//            headers.setBasicAuth("user", "the_cake") // or admin credentials
//    ).build();
//
//    public Mono<Void> verifyFlightExists(String flightId) {
//        return webClient.get()
//                .uri("/flights/{id}", flightId)
//                .retrieve()
//                .toBodilessEntity().then();
//    }
//
//}
