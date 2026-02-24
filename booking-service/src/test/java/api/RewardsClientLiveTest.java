package api;

import com.baeldung.springsoap.client.gen.CaptureRewardsResponse;
import entelect.training.incubator.spring.booking.api.RewardsApiClient;
import entelect.training.incubator.spring.booking.config.RewardsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RewardsConfig.class, loader = AnnotationConfigContextLoader.class)
public class RewardsClientLiveTest {

    @Autowired
    RewardsApiClient client;

    @Test
    public void test(){
        CaptureRewardsResponse captureRewardsResponse =  client.captureRewards("A0924531", BigDecimal.ONE);
        System.out.println(captureRewardsResponse);
        System.out.println(
                captureRewardsResponse.getBalance()
        );
        //assertEquals()
    }
}
