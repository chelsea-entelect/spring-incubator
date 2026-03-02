package entelect.training.incubator.spring.booking.legacy.model

import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Getter
@Setter
@Table("bookings")
class Booking(private val customerId: String?, private val flightId: String?, private val referenceNumber: String?) {
    @Id
    private val id: Int? = null
}
