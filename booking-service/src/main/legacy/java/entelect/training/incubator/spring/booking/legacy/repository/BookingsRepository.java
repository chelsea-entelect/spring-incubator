//package entelect.training.incubator.spring.booking.legacy.repository;
//
//import entelect.training.incubator.spring.booking.model.Booking;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
////@Repository
//public interface BookingsRepository extends ReactiveCrudRepository<Booking, Integer> {
//    Mono<Booking> getBookingById(Integer id);
//
//    Flux<Booking> getBookingsByCustomerId(String customerId);
//
//    Flux<Booking> getBookingsByReferenceNumber(String referenceNumber);
//}