//package entelect.training.incubator.spring.booking.legacy.config;
//
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//
//public class SecurityConfig {
//
//
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
//                .build();
//    }
//}