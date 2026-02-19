package entelect.training.incubator.spring.booking.controller;

import entelect.training.incubator.spring.booking.api.CustomerApiClient;
import entelect.training.incubator.spring.booking.api.FlightApiClient;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingRequest;
import entelect.training.incubator.spring.booking.service.BookingsService;
import entelect.training.incubator.spring.flight.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import entelect.training.incubator.spring.customer.model.Customer;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("bookings")
public class BookingsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);

    CustomerApiClient customerApiClient;
    FlightApiClient flightApiClient;
    BookingsService bookingsService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
        LOGGER.info("Processing booking request for booking={}", bookingRequest);
        Customer customer = customerApiClient.getCustomerById(bookingRequest.getCustomerId());
        LOGGER.info("API call to check client{}", customer);
        if(Objects.isNull(customer)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        Flight flight = flightApiClient.getFlightsById(bookingRequest.getFlightId());
//        if(Objects.isNull(flight)){
//            return;
//        }
        Booking booking = new Booking();
        booking.setCustomerId(customer.getId().toString());

        final Customer savedCustomer = bookingsService.createBooking(customer);


        LOGGER.trace("Booking created");
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<?> getBookingById(@PathVariable Integer id){
//
//    }
//
//    @PostMapping("search")
//    public ResponseEntity<?> getBookingByCustomerId(@RequestBody ){
//
//    }
//
//    @PostMapping("/search")
//    public ResponseEntity<?> getBookingsByRefNum(@RequestBody ){
//
//    }
}
