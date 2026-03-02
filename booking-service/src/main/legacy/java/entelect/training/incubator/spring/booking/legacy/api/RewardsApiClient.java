//package entelect.training.incubator.spring.booking.api;
//
//import com.baeldung.springsoap.client.gen.CaptureRewardsRequest;
//import com.baeldung.springsoap.client.gen.CaptureRewardsResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.math.BigDecimal;
//
//
//public class RewardsApiClient extends WebServiceGatewaySupport {
//
//    public Mono<CaptureRewardsResponse> captureRewards(
//            String customerId,
//            BigDecimal amount
//    ) {
//        return Mono.fromCallable(() -> {
//                    CaptureRewardsRequest request = new CaptureRewardsRequest();
//                    request.setPassportNumber(customerId);
//                    request.setAmount(amount);
//
//                    return (CaptureRewardsResponse)
//                            getWebServiceTemplate()
//                                    .marshalSendAndReceive(request);
//                })
//                .subscribeOn(Schedulers.boundedElastic()); // SOAP is blocking
//    }
//}
