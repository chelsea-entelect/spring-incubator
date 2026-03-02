package entelect.training.incubator.spring.booking.error

class CustomerServiceUnavailableException(customerId: String?) : RuntimeException("Customer not found: " + customerId)
