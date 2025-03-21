package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.ExRateProviderStub;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static java.math.BigDecimal.valueOf;

@Configuration // 스프링이 사용하는 구성정보
//@ComponentScan // 스프링이 알아서 컴포넌트를 스캔하여 의존관계를 내부적으로 설정하게하는 어노테이션
public class TestPaymentConfig { // 오브젝트팩토리를 이용해 생성자 주입을 하는 것이 정통적 방식.
    @Bean
    public PaymentService paymentService(){
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(valueOf(1_000));
    }

    @Bean
    public Clock clock(){
        return Clock.fixed(Instant.now(), ZoneId.systemDefault()); // 테스트에는 고정된 시간이 편리하다.
    }
}
