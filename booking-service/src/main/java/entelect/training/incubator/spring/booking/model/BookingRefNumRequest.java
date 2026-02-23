package entelect.training.incubator.spring.booking.model;

import lombok.Data;

@Data
public class BookingRefNumRequest {

    private String referenceNumber;

    public BookingRefNumRequest(String referenceNumber){
        this.referenceNumber = referenceNumber;
    }
}
