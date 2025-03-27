package tobyspring.hellospring.exrate;

import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;

//@Component // 컴포넌트로 등록된 프로바이더가 주입되어 실행된다.
public class SimpleExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        if (currency.equals("USD")) return BigDecimal.valueOf(1000);

        throw new IllegalArgumentException("지원되지 않는 통화입니다.");
    }
}
