package entelect.training.incubator.spring.booking.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("bookings")
public class Booking {

    @Id
    private Integer id;

    private String customerId;

    private String flightId;

    private String referenceNumber;

    public Booking(String customerId, String flightId, String referenceNumber ){
        this.customerId = customerId;
        this.flightId = flightId;
        this.referenceNumber = referenceNumber;
    }

}
