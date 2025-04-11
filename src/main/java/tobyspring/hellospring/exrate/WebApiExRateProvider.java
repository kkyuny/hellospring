package tobyspring.hellospring.exrate;

import org.springframework.stereotype.Component;
import tobyspring.hellospring.api.*;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;

@Component // 클라이언트에서 등록 된 컴포턴트를 이용해서 의존관계를 설정한다.
public class WebApiExRateProvider implements ExRateProvider {
    /*
     한 번 만들고 계속 사용할 인스턴스라면 스프링 빈으로 등록해서 사용한다.
     하지만 게속해서 등록하고 수정하고 공유되는 정보는 싱글톤으로 사용하면 안된다.
     */
    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
        
        return apiTemplate.getExRate(url); // 여기서 사용할 Executor를 선택해서 사용할 수 있다.
    }


}
