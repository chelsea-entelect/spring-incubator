package entelect.training.incubator.spring.booking.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BookingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerId;

    private String flightId;

    public BookingRequest(String customerId, String flightId){
        this.customerId = customerId;
        this.flightId= flightId;
    }
}
