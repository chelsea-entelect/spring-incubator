package config;

import entelect.training.incubator.spring.booking.BookingsServiceApplication;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookingsServiceApplication.class
)
@AutoConfigureWebTestClient
class SecurityConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    class BookingsSecurity {

        @Test
        void bookingsPostRequiresUserRole() {
            webTestClient.post()
                    .uri("/bookings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isUnauthorized();

            webTestClient.post()
                    .uri("/bookings")
                    .headers(headers -> headers.setBasicAuth("user", "password"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().is4xxClientError();
        }

        @Test
        void bookingsGetRequiresUserRole() {
            webTestClient.get()
                    .uri("/bookings/1")
                    .exchange()
                    .expectStatus().isUnauthorized();

            webTestClient.get()
                    .uri("/bookings/1")
                    .headers(headers -> headers.setBasicAuth("user", "password"))
                    .exchange()
                    .expectStatus().isOk();
        }
    }

    @Nested
    class FlightsSecurity {

        @Test
        void flightsGetByIdIsPublic() {
            webTestClient.get()
                    .uri("/flights/1")
                    .exchange()
                    .expectStatus().isNotFound(); // no controller in this service, but no 401
        }

        @Test
        void flightsSpecialsRequiresLoyaltyUser() {
            webTestClient.get()
                    .uri("/flights/specials")
                    .exchange()
                    .expectStatus().isUnauthorized();

            webTestClient.get()
                    .uri("/flights/specials")
                    .headers(headers -> headers.setBasicAuth("user", "password"))
                    .exchange()
                    .expectStatus().isForbidden();

            webTestClient.get()
                    .uri("/flights/specials")
                    .headers(headers -> headers.setBasicAuth("loyal", "password"))
                    .exchange()
                    .expectStatus().isNotFound(); // security passes, but no handler in this service
        }

        @Test
        void flightsPostRequiresAdmin() {
            webTestClient.method(HttpMethod.POST)
                    .uri("/flights")
                    .exchange()
                    .expectStatus().isUnauthorized();

            webTestClient.method(HttpMethod.POST)
                    .uri("/flights")
                    .headers(headers -> headers.setBasicAuth("user", "password"))
                    .exchange()
                    .expectStatus().isForbidden();

            webTestClient.method(HttpMethod.POST)
                    .uri("/flights")
                    .headers(headers -> headers.setBasicAuth("admin", "password"))
                    .exchange()
                    .expectStatus().is4xxClientError(); // security passed, controller may still reject
        }
    }

    @Nested
    class CustomersSecurity {

        @Test
        void customersPostRequiresAdmin() {
            webTestClient.post()
                    .uri("/customers")
                    .exchange()
                    .expectStatus().isUnauthorized();

            webTestClient.post()
                    .uri("/customers")
                    .headers(headers -> headers.setBasicAuth("user", "password"))
                    .exchange()
                    .expectStatus().isForbidden();

            webTestClient.post()
                    .uri("/customers")
                    .headers(headers -> headers.setBasicAuth("admin", "password"))
                    .exchange()
                    .expectStatus().is4xxClientError();
        }

        @Test
        void customersGetRequiresUser() {
            webTestClient.get()
                    .uri("/customers/1/")
                    .exchange()
                    .expectStatus().isUnauthorized();

            webTestClient.get()
                    .uri("/customers/1/")
                    .headers(headers -> headers.setBasicAuth("user", "password"))
                    .exchange()
                    .expectStatus().is4xxClientError();
        }
    }
}

