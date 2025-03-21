package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class) // 스프링 테스트를 사용하기 위한 기능 확장 코드
@ContextConfiguration(classes = TestPaymentConfig.class) // 이놈 설명 적자.
class PaymentServiceSpringTest {
    // 스프링 테스트의 장점은 컨피그가 만든 빈을 가져와서 사용할 수 있다는 장점이 있다.
    @Autowired
    PaymentService paymentService;
    @Autowired
    ExRateProviderStub exRateProviderStub;
    @Autowired
    Clock clock;

    @Test
    void convertedAmount() throws IOException {
        // BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestPaymentConfig.class);
        //exRate: 1000(기본 설정 값)
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualTo(valueOf(10_000));

        //exRate: 500
        Payment payment2 = paymentService.prepare(1L, "USD", TEN);

        exRateProviderStub.setExRate(valueOf(500)); // exRateProviderStub를 클래스타입으로 DI하여 값을 직접 제어할 수 있다.
        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment2.getConvertedAmount()).isEqualTo(valueOf(10_000));
    }

    @Test
    void validUntil() throws IOException {
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // valid until이 prepare() 30분 뒤로 설정 됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        // paymentService에서 설정한 유효시간이 실제로 유효한지 테스트하는 부분이다. 이 때 실제시간보다는 고정된 시간을 사용하는것이 유리하다.
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }
}