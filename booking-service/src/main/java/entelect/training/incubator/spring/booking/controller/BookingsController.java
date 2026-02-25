package entelect.training.incubator.spring.booking.controller;

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

@RestController
@RequestMapping("bookings")
public class BookingsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);


    BookingsService bookingsService;

    public BookingsController(
            BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public Mono<ResponseEntity<Booking>> createBooking(
            @RequestBody BookingRequest bookingRequest) {

        return bookingsService
                .createBooking(bookingRequest)
                .map(saved ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(saved)
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
