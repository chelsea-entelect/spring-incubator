package entelect.training.incubator.spring.booking.api

import com.baeldung.springsoap.client.gen.CaptureRewardsRequest
import com.baeldung.springsoap.client.gen.CaptureRewardsResponse
import org.springframework.stereotype.Component
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.math.BigDecimal
import java.util.concurrent.Callable


@Component
class RewardsApiClient : WebServiceGatewaySupport() {
    fun captureRewards(
        customerId: String?,
        amount: BigDecimal?
    ): Mono<CaptureRewardsResponse?> {
        return Mono.fromCallable<CaptureRewardsResponse?>(Callable {
            val request = CaptureRewardsRequest()
            request.setPassportNumber(customerId)
            request.setAmount(amount)
            getWebServiceTemplate()
                .marshalSendAndReceive(request) as CaptureRewardsResponse?
        })
            .subscribeOn(Schedulers.boundedElastic()) // SOAP is blocking
    }
}