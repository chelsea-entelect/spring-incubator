package entelect.training.incubator.spring.booking.error

class CustomerNotFoundException(customerId: String?) : RuntimeException("Customer not found: " + customerId)