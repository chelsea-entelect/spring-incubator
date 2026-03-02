package entelect.training.incubator.spring.booking.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("bookings")
data class Booking(

    val customerId: String,
    val flightId: String,
    val referenceNumber: String,
    @Id
    val id: Int? = null
)