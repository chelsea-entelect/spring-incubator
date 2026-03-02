package entelect.training.incubator.spring.booking.model

import org.springframework.context.annotation.Role
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    val id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var enabled: Boolean,
    var tokenExpired: Boolean,
    var roles: Collection<Role>
)