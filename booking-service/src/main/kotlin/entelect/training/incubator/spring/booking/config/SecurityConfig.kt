package entelect.training.incubator.spring.booking.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService {
        val user = User.withUsername("user")
            .passwordEncoder(passwordEncoder::encode)
            .password("password")
            .roles("USER")
            .build()

        val loyaltyUser = User.withUsername("loyal")
            .passwordEncoder(passwordEncoder::encode)
            .password("password")
            .roles("USER", "LOYALTY_USER")
            .build()

        val admin = User.withUsername("admin")
            .passwordEncoder(passwordEncoder::encode)
            .password("password")
            .roles("USER", "ADMIN")
            .build()

        return MapReactiveUserDetailsService(user, loyaltyUser, admin)
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .httpBasic(Customizer.withDefaults())
            .formLogin { it.disable() }
            .authorizeExchange { exchanges ->
                // Customers
                exchanges
                    .pathMatchers(HttpMethod.POST, "/customers").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.GET, "/customers/*").hasRole("USER")

                    // Flights
                    .pathMatchers(HttpMethod.POST, "/flights").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.GET, "/flights/specials").hasRole("LOYALTY_USER")
                    .pathMatchers(HttpMethod.GET, "/flights/*").permitAll()

                    // Bookings
                    .pathMatchers(HttpMethod.POST, "/bookings").hasRole("USER")
                    .pathMatchers(HttpMethod.GET, "/bookings/*").hasRole("USER")

                    .pathMatchers(
                        "/actuator/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                    ).permitAll()

                    // Everything else must be authenticated
                    .anyExchange().authenticated()
            }
            .build()
    }
}