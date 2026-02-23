package controller;

import entelect.training.incubator.spring.booking.BookingsServiceApplication;
import entelect.training.incubator.spring.booking.api.CustomerApiClient;
import entelect.training.incubator.spring.booking.api.FlightApiClient;
import entelect.training.incubator.spring.booking.config.SecurityConfig;
import entelect.training.incubator.spring.booking.error.CustomerNotFoundException;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingRequest;
import entelect.training.incubator.spring.booking.repository.BookingsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookingsServiceApplication.class,
        properties = {"spring.security.enabled=false",
                "management.security.enabled=false"}
)
@Import(SecurityConfig.class)
class BookingsRestControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookingsRepository bookingsRepository;


    @MockBean
    private FlightApiClient flightApiClient;
    @MockBean
    private CustomerApiClient customerApClient;

    @AfterEach
    void resetDb() {
        bookingsRepository.deleteAll();
    }

    @Test
    void whenValidCustomerAndFlight_thenCreateBooking() throws Exception {

        // given
        String customerId = "1";
        String flightId = "100";
        BookingRequest request = new BookingRequest(customerId, flightId);

        // mock external service calls
        when(customerApClient.verifyCustomerExists(customerId)).thenReturn(Mono.empty());
        when(flightApiClient.verifyFlightExists(flightId)).thenReturn(Mono.empty());

        // when
        webTestClient
                .post().uri("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request).exchange()
                .expectStatus().isCreated();

        // then
        List<Booking> found =  bookingsRepository.findAll().collectList().block();

        assertThat(found)
                .extracting(Booking::getCustomerId, Booking::getFlightId)
                .containsOnly(tuple(customerId, flightId));
        assertThatCode(() -> UUID.fromString(found.getFirst().getReferenceNumber()))
                .doesNotThrowAnyException();
    }

    @Test
    void whenValidCustomerAndInvalidFlight_thenDoNotCreateBooking() throws Exception {

        // given
        String customerId = "1";
        String flightId = "100";
        BookingRequest request = new BookingRequest(customerId, flightId);

        // mock external service calls
        when(customerApClient.verifyCustomerExists(customerId)).thenReturn(Mono.empty());
        when(flightApiClient.verifyFlightExists(flightId))
                .thenReturn(Mono.error(new CustomerNotFoundException(customerId)));

        webTestClient
                .post()
                .uri("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();


    }




}
