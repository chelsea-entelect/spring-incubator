package entelect.training.incubator.spring.booking.repository

import entelect.training.incubator.spring.booking.model.Booking
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
 interface BookingsRepository: ReactiveCrudRepository<Booking, Long> {
    fun getBookingById(id: Int?): Mono<Booking?>?
    fun getBookingsByCustomerId(customerId : String?): Flux<Booking>;
    fun getBookingsByReferenceNumber(referenceNumber: String?): Flux<Booking>;

}