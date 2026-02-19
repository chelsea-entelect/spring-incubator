package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entelect.training.incubator.spring.booking.BookingsServiceApplication;
import entelect.training.incubator.spring.booking.api.CustomerApiClient;
import entelect.training.incubator.spring.booking.api.FlightApiClient;
import entelect.training.incubator.spring.booking.config.SecurityConfig;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingRequest;
import entelect.training.incubator.spring.booking.repository.BookingsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookingsServiceApplication.class
)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class BookingsRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

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
    @WithMockUser(username = "testuser", roles = {"USER"})  // <-- Mock an authenticated user
    void whenValidCustomerAndFlight_thenCreateBooking() throws Exception {

        // given
        String customerId = "1";
        String flightId = "100";
        BookingRequest request = new BookingRequest(customerId, flightId);

        // mock external service calls
        doNothing().when(customerApClient).verifyCustomerExists(customerId);
        doNothing().when(flightApiClient).verifyFlightExists(flightId);

        // when
        mvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isCreated());

        // then
        List<Booking> found = (List<Booking>) bookingsRepository.findAll();

        assertThat(found)
                .extracting(Booking::getCustomerId, Booking::getFlightId)
                .containsOnly(tuple(customerId, flightId));
    }

    private static String toJson(final Object obj) {
        try {
            return new ObjectMapper()
                    .setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
