package entelect.training.incubator.spring.booking.api

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class FlightApiClient {

    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8202")
        .defaultHeaders(
        {
            headers: HttpHeaders? ->
            headers!!.setBasicAuth("user", "the_cake")
        }
    ).build()

    fun verifyFlightExists(flightId: String): Mono<Void>{
        return webClient.get().uri("/flights/{id}", flightId)
            .retrieve().toBodilessEntity().then();
    }
}