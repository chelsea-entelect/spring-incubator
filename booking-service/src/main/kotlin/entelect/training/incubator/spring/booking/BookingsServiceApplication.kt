package entelect.training.incubator.spring.booking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookingsServiceApplication

fun main(args: Array<String>) {
    runApplication<BookingsServiceApplication>(*args)
}