package entelect.training.incubator.spring.booking.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Map


@RestControllerAdvice
class GlobalErrorHandler {
    @ExceptionHandler(CustomerNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCustomerNotFound(ex: CustomerNotFoundException): MutableMap<String?, String?> {
        return Map.of<String?, String?>(
            "error", "CUSTOMER_NOT_FOUND",
            "message", ex.message
        )
    }
}
