package entelect.training.incubator.spring.booking.controller

import entelect.training.incubator.spring.booking.model.Booking
import entelect.training.incubator.spring.booking.model.BookingRequest
import entelect.training.incubator.spring.booking.service.BookingsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Function


@RestController
@RequestMapping("bookings")
class BookingsController(var bookingsService: BookingsService) {
    private val LOGGER: Logger? = LoggerFactory.getLogger(BookingsController::class.java)


    @PostMapping
    fun createBooking(
        @RequestBody bookingRequest: BookingRequest
    ): Mono<ResponseEntity<Booking>> {

        return bookingsService
            .createBooking(bookingRequest)
            .map { saved ->
                ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(saved)
            }
    }

    @GetMapping("/{id}")
    fun getBookingById(@PathVariable id: Int?): Mono<Booking?>? {
        return bookingsService.getBookingById(id)
    }

    // Changed the requirement slightly to be more RESTful
    @GetMapping("/search/{customerId}")
    fun getBookingByCustomerId(@PathVariable customerId: String?): Flux<Booking> {
        return bookingsService.getBookingsByCustomerId(customerId)
    }

    // Changed the requirement slightly to be more RESTful
    @GetMapping("/search/{reference}")
    fun  getBookingsByRefNum(@PathVariable reference: String?): Flux<Booking>{
        return bookingsService.getBookingByReferenceNumber(reference);
    }
}