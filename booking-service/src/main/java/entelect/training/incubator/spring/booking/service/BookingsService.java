package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.repository.BookingsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookingsService {

    final BookingsRepository bookingsRepository;

    public BookingsService(BookingsRepository bookingsRepository){
        this.bookingsRepository = bookingsRepository;
    }
    public Mono<Booking> createBooking(Booking booking) {
        return bookingsRepository.save(booking);

    }

    public Mono<Booking> getBookingById(Integer id){
        return bookingsRepository.getBookingById(id);
    }

    public Flux<Booking> getBookingsByCustomerId(String id){
        return bookingsRepository.getBookingsByCustomerId(id);
    }

    public Flux<Booking> getBookingByReferenceNumber(String referenceNumber) {
        return bookingsRepository.getBookingsByReferenceNumber(referenceNumber);
    }
}
