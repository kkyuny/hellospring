package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan // 스프링이 알아서 의존관계를 내부적으로 설정하게하는 어노테이션
public class ObjectFactory {
    /*@Bean
    public PaymentService paymentService(){
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }*/
}
