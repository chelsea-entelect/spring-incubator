package entelect.training.incubator.spring.booking.api

import entelect.training.incubator.spring.booking.error.CustomerNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate

@Component
class CustomerApiClient {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8201")
        .defaultHeaders(Consumer { headers: HttpHeaders? ->
            headers!!.setBasicAuth(
                "user",
                "the_cake"
            )
        } // or admin credentials
        ).build()

    fun verifyCustomerExists(customerId: String): Mono<Void?> {
        return webClient.get()
            .uri("/customers/{id}", customerId)

            .retrieve()
            .onStatus(
                Predicate { status: HttpStatusCode? -> status === HttpStatus.NOT_FOUND },
                Function { r: ClientResponse? -> Mono.error<Throwable?>(CustomerNotFoundException(customerId)) }
            ) //                .onStatus(
            //                        HttpStatus::is5xxServerError,
            //                        r -> Mono.error(new CustomerServiceUnavailableException())
            //                )
            .toBodilessEntity()
            .then()
    }
}