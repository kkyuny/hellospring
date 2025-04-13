package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.exrate.SimpleExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration // 스프링이 사용하는 구성정보
//@ComponentScan // 스프링이 알아서 컴포넌트를 스캔하여 의존관계를 내부적으로 설정하게하는 어노테이션
public class PaymentConfig { // 오브젝트팩토리를 이용해 생성자 주입을 하는 것이 정통적 방식. 이름은 페이먼트 컨피그로 수정
    @Bean
    public PaymentService paymentService(){
        return new PaymentService(exRateProvider(), clock());
    }

    /*@Bean
    public ApiTemplate apiTemplate(){
        return new ApiTemplate(new SimpleApiExecutor(), new ErApiExtractor());
    }*/

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public Clock clock(){
        // 클럭을 생성해두면 시계와 같은 역할로 계속 사용할 수 있다.
        // LocalDateTime.now()를 사용할 수도 있지만, Clock을 사용하면 시간을 고정하여서도 쓸 수 있다.
        return Clock.systemDefaultZone();
    }
}
