//package entelect.training.incubator.spring.booking.legacy.config;
//
//import entelect.training.incubator.spring.booking.api.RewardsApiClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//
//
//public class RewardsConfig {
//
//    @Value("${rewards.endpoint}")
//    private String rewardsEndpoint;
//
//    @Bean
//    public Jaxb2Marshaller marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setContextPath("com.baeldung.springsoap.client.gen");
//        return marshaller;
//    }
//
//    @Bean
//    public RewardsApiClient rewardsClient(Jaxb2Marshaller marshaller) {
//        RewardsApiClient client = new RewardsApiClient();
//        client.setDefaultUri(rewardsEndpoint);
//        client.setMarshaller(marshaller);
//        client.setUnmarshaller(marshaller);
//        return client;
//    }
//}
