package entelect.training.incubator.spring.booking.service

import entelect.training.incubator.spring.booking.api.CustomerApiClient
import entelect.training.incubator.spring.booking.api.FlightApiClient
import entelect.training.incubator.spring.booking.api.RewardsApiClient
import entelect.training.incubator.spring.booking.model.Booking
import entelect.training.incubator.spring.booking.model.BookingRequest
import entelect.training.incubator.spring.booking.repository.BookingsRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*

@Service
class BookingsService (
    private var bookingsRepository: BookingsRepository,
    private var customerApiClient: CustomerApiClient,
    private var flightApiClient: FlightApiClient,
    private var rewardsApiClient: RewardsApiClient
){
    fun createBooking(request: BookingRequest): Mono<Booking> {
        return customerApiClient.verifyCustomerExists(request.customerId)
            .then(flightApiClient.verifyFlightExists(request.flightId))
            .then(
                bookingsRepository.save(
                    Booking(
                        request.customerId,
                        request.flightId,
                        UUID.randomUUID().toString()
                    )
                )
            )
            .flatMap { savedBooking ->
                rewardsApiClient
                    .captureRewards(request.customerId, BigDecimal.ONE)
                    .thenReturn(savedBooking)
            }
    }

    fun getBookingById(id: Int?): Mono<Booking?>? {
        return bookingsRepository.getBookingById(id)
    }

    fun getBookingsByCustomerId(id: String?): Flux<Booking> {
        return bookingsRepository.getBookingsByCustomerId(id)
    }

    fun getBookingByReferenceNumber(referenceNumber: String?): Flux<Booking> {
        return bookingsRepository.getBookingsByReferenceNumber(referenceNumber)
    }


}