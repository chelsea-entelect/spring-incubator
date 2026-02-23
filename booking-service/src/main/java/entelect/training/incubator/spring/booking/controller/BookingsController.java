package entelect.training.incubator.spring.booking.controller;

import entelect.training.incubator.spring.booking.api.CustomerApiClient;
import entelect.training.incubator.spring.booking.api.FlightApiClient;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingCustomerIdRequest;
import entelect.training.incubator.spring.booking.model.BookingRequest;
import entelect.training.incubator.spring.booking.service.BookingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("bookings")
public class BookingsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);

    CustomerApiClient customerApiClient;
    FlightApiClient flightApiClient;
    BookingsService bookingsService;

    public BookingsController(CustomerApiClient customerApiClient,
                              FlightApiClient flightApiClient,
                              BookingsService bookingsService) {
        this.customerApiClient = customerApiClient;
        this.flightApiClient = flightApiClient;
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public Mono<ResponseEntity<Booking>> createBooking(
            @RequestBody BookingRequest bookingRequest) {

        return customerApiClient.verifyCustomerExists(bookingRequest.getCustomerId())
                .then(flightApiClient.verifyFlightExists(bookingRequest.getFlightId()))
                .then(
                        bookingsService.createBooking(
                                new Booking(
                                        bookingRequest.getCustomerId(),
                                        bookingRequest.getFlightId(),
                                        UUID.randomUUID().toString()
                                )
                        )
                )
                .map(savedBooking ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(savedBooking)
                );
    }

    @GetMapping("/{id}")
    public Mono<Booking> getBookingById(@PathVariable Integer id){
        return bookingsService.getBookingById(id);
    }

    @PostMapping("/search")
    public Flux<Booking> getBookingByCustomerId(@RequestBody BookingCustomerIdRequest bookingCustomerIdRequest){
        return bookingsService.getBookingsByCustomerId(bookingCustomerIdRequest.getCustomerId());
    }

//    @PostMapping("/search")
//    public Flux<Booking> getBookingsByRefNum(@RequestBody BookingRefNumRequest bookingRefNumRequest){
//        return bookingsService.getBookingByReferenceNumber(bookingRefNumRequest.getReferenceNumber());
//
//    }
}
