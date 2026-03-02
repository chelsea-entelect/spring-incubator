package entelect.training.incubator.spring.booking.legacy.error;

public class CustomerServiceUnavailableException extends RuntimeException {
    public CustomerServiceUnavailableException(String customerId) {
        super("Customer not found: " + customerId);
    }
}
