package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    // 해당하는 기존 코드에서 테스트를 제네레이트 할 수 있다.
    @Test
    @DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {
        testAmount(valueOf(500), valueOf(5_000));
        testAmount(valueOf(1_000), valueOf(10_000));
        testAmount(valueOf(3_000), valueOf(30_000));

        // 원화환산금액의 유효시간 계산
        //assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        //assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        /* 외부의 api를 제어하는 테스트는 외부 환경 변경 등이 있을 때 검증에 위험하다.
            그래서 외부 api를 대체하는 인터페이스(임포스터, 스텁 등으로 부른다.)를 만들어 대체하여 테스트한다.
            또, PaymentService의 기능이 확장하더라도 테스트 코드의 수정이 필요없고, 내가 원하는 기능만 테스트 할 수 있다.
         */
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate)); // stub에서 외부에서 값을 가져왔다고 가정하고 특정한 값을 제공한다.

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // 환율정보 가져온다.
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate); // 빅데시말에서는 isEqualByComparingTo를 사용하여 비교하는 것이 좋다.
        // 원화환산금액 계산
        assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount); // 자리수 구분용으로 _를 사용해도 된다.
    }
}