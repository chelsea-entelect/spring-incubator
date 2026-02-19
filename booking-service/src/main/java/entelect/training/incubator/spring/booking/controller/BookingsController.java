package entelect.training.incubator.spring.booking.controller;

import entelect.training.incubator.spring.booking.api.CustomerApiClient;
import entelect.training.incubator.spring.booking.api.FlightApiClient;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingRequest;
import entelect.training.incubator.spring.booking.service.BookingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
        LOGGER.info("Processing booking request for booking={}", bookingRequest);
        customerApiClient.verifyCustomerExists(bookingRequest.getCustomerId());
        LOGGER.info("API call checked that customer exists");

        flightApiClient.verifyFlightExists(bookingRequest.getFlightId());

        Booking booking = new Booking(bookingRequest.getCustomerId(), bookingRequest.getFlightId(), "44GH"); //Hard code ref for now

        bookingsService.createBooking(booking);
        LOGGER.trace("Booking created");
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
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
