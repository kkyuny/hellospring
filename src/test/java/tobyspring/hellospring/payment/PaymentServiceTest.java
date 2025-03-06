package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    // 해당하는 기존 코드에서 테스트를 제네레이트 할 수 있다.
    @Test
    @DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족했는지 검증")
    void prepare() throws IOException {
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider());

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율정보 가져온다.
        Assertions.assertThat(payment.getExRate()).isNotNull();

        // 원화환산금액 계산
        Assertions.assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));

        // 원화환산금액의 유효시간 계산
        Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }
}