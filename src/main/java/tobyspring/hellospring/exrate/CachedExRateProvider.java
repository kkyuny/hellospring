package tobyspring.hellospring.exrate;

import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider target;

    private BigDecimal cachedExRate;
    private LocalDateTime exExpiryTime;

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        if(cachedExRate == null || exExpiryTime.isBefore(LocalDateTime.now())){
            cachedExRate = this.target.getExRate(currency);
            exExpiryTime = LocalDateTime.now().plusSeconds(3);

            System.out.println("Cache Updated");
        }
        return cachedExRate;
    }
}
