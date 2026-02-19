package entelect.training.incubator.spring.booking.repository;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingsRepository extends CrudRepository<Booking, Integer> {

    Optional<Booking> findByFirstNameAndLastName(String firstName, String lastName);
}