package entelect.training.incubator.spring.booking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCustomerIdRequest {
    private String customerId;

    BookingCustomerIdRequest(String customerId){
        this.customerId = customerId;
    }
}
