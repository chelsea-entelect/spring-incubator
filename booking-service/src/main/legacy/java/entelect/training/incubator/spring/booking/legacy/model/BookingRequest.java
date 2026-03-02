package entelect.training.incubator.spring.booking.legacy.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BookingRequest {
    @Id
    private Integer id;

    private String customerId;

    private String flightId;

    public BookingRequest(String customerId, String flightId){
        this.customerId = customerId;
        this.flightId= flightId;
    }
}
