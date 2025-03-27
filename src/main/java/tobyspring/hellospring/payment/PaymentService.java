package tobyspring.hellospring.payment;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class PaymentService {
    // 생성자를 통해 전달받은 클래스는 저장을 해야 사용할 수 있기 때문에 선언하는 것이다.
    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }
    /*
       public PaymentService() {
          this.exRateProvider = new WebApiExRateProvider();
       }
       의존관계가 설정됐다고 표현한다.
       클래스를 변경할 때 마다 클래스와 메서드 이름을 계속 변경해야한다.
       인터페이스도 사용하는 메서드의 클래스에 의존한다.

       PaymentService가 관계설정의 책임과 분리 역할을 맡는다.
       Simple과 WebApi 둘 중에서 생성자를 선택해서 사용한다.


     */

    /*
        public PaymentService(ExRateProvider exRateProvider) {
          this.exRateProvider = exRateProvider;
        }
        위와 같이 생성자를 주입받아 사용하면 클라이언트 측에서 관계설정의 책임과 분리 역할을 맡는다.
     */

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount){
        // https://open.er-api.com/v6/latest/USD
        BigDecimal exRate = exRateProvider.getExRate(currency);

        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRate, LocalDateTime.now(clock));
    }
}
