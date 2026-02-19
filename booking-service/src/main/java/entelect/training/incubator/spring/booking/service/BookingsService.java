package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.repository.BookingsRepository;
import entelect.training.incubator.spring.customer.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class BookingsService {

    final BookingsRepository bookingsRepository;

    public BookingsService(BookingsRepository bookingsRepository){
        this.bookingsRepository = bookingsRepository;
    }
    public Booking createBooking(Booking booking) {
        return bookingsRepository.save(booking);
    }

}
