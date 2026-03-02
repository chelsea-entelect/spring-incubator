package entelect.training.incubator.spring.booking.config

import entelect.training.incubator.spring.booking.api.RewardsApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller


@Configuration
@ConditionalOnProperty(name = ["rewards.endpoint"], matchIfMissing = false)
class RewardsConfig {
    @Value("\${rewards.endpoint}")
    private val rewardsEndpoint: String? = null

    @Bean
    fun marshaller(): Jaxb2Marshaller {
        val marshaller = Jaxb2Marshaller()
        marshaller.setContextPath("com.baeldung.springsoap.client.gen")
        return marshaller
    }

    @Bean
    fun rewardsClient(marshaller: Jaxb2Marshaller?): RewardsApiClient {
        val client: RewardsApiClient = RewardsApiClient()
        client.setDefaultUri(rewardsEndpoint)
        client.setMarshaller(marshaller)
        client.setUnmarshaller(marshaller)
        return client
    }
}