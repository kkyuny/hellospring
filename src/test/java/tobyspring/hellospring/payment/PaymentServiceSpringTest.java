package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.ObjectFactory;
import tobyspring.hellospring.TestObjectFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class) // 스프링 테스트를 사용하기 위한 기능 확장 코드
@ContextConfiguration(classes = TestObjectFactory.class) // 이놈 설명 적자.
class PaymentServiceSpringTest {
    @Autowired
    BeanFactory beanFactory;
    @Autowired
    ExRateProviderStub exRateProviderStub;

    @Test
    void convertedAmount() throws IOException {
        // BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

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

}