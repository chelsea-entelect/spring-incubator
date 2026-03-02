//package entelect.training.incubator.spring.booking.legacy.service;
//
//import entelect.training.incubator.spring.booking.api.CustomerApiClient;
//import entelect.training.incubator.spring.booking.api.FlightApiClient;
//import entelect.training.incubator.spring.booking.api.RewardsApiClient;
//import entelect.training.incubator.spring.booking.model.Booking;
//import entelect.training.incubator.spring.booking.legacy.model.BookingRequest;
//import entelect.training.incubator.spring.booking.repository.BookingsRepository;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Service
//public class BookingsService {
//
//    final BookingsRepository bookingsRepository;
//    final FlightApiClient flightApiClient;
//    final CustomerApiClient customerApiClient;
//    final RewardsApiClient rewardsApiClient;
//
//    public BookingsService(BookingsRepository bookingsRepository, CustomerApiClient customerApiClient, FlightApiClient flightApiClient, RewardsApiClient rewardsApiClient){
//        this.bookingsRepository = bookingsRepository;
//        this.customerApiClient = customerApiClient;
//        this.flightApiClient = flightApiClient;
//        this.rewardsApiClient = rewardsApiClient;
//    }
//
//    //Business logic needs to occur within the service layer
//    public Mono<Booking> createBooking(BookingRequest request) {
//
//        return customerApiClient.verifyCustomerExists(request.getCustomerId())
//                .then(flightApiClient.verifyFlightExists(request.getFlightId()))
//                .then(
//                        bookingsRepository.save(
//                                new Booking(
//                                        request.getCustomerId(),
//                                        request.getFlightId(),
//                                        UUID.randomUUID().toString()
//                                )
//                        )
//                )
//
//                .flatMap(savedBooking ->
//                        rewardsApiClient
//                                .captureRewards(
//                                        request.getCustomerId(),
//                                        BigDecimal.ONE
//                                )
//                                .thenReturn(savedBooking)
//                );
//    }
//
//
//    public Mono<Booking> getBookingById(Integer id){
//        return bookingsRepository.getBookingById(id);
//    }
//
//    public Flux<Booking> getBookingsByCustomerId(String id){
//        return bookingsRepository.getBookingsByCustomerId(id);
//    }
//
//    public Flux<Booking> getBookingByReferenceNumber(String referenceNumber) {
//        return bookingsRepository.getBookingsByReferenceNumber(referenceNumber);
//    }
//
//
//}
