package entelect.training.incubator.spring.booking.api;

import com.baeldung.springsoap.client.gen.CaptureRewardsRequest;
import com.baeldung.springsoap.client.gen.CaptureRewardsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigDecimal;

public class RewardsApiClient extends WebServiceGatewaySupport {

    public CaptureRewardsResponse captureRewards(String passportNumber, BigDecimal amount){
        CaptureRewardsRequest captureRewardsRequest = new CaptureRewardsRequest();
        captureRewardsRequest.setPassportNumber(passportNumber);
        captureRewardsRequest.setAmount(amount);
        CaptureRewardsResponse captureRewardsResponse = (CaptureRewardsResponse) getWebServiceTemplate().marshalSendAndReceive(captureRewardsRequest);
        return captureRewardsResponse;
    }
}
