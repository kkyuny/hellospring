package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.exrate.CachedExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

@Configuration // 스프링이 사용하는 구성정보
//@ComponentScan // 스프링이 알아서 컴포넌트를 스캔하여 의존관계를 내부적으로 설정하게하는 어노테이션
public class ObjectFactory { // 오브젝트팩토리를 이용해 생성자 주입을 하는 것이 정통적 방식.
    @Bean
    public PaymentService paymentService(){
        return new PaymentService(cachedExRateProvider());
    }

    @Bean
    public CachedExRateProvider cachedExRateProvider(){
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }
}
