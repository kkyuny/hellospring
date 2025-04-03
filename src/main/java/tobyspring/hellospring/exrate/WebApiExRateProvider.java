package tobyspring.hellospring.exrate;

import org.springframework.stereotype.Component;
import tobyspring.hellospring.api.*;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component // 클라이언트에서 등록 된 컴포턴트를 이용해서 의존관계를 설정한다.
public class WebApiExRateProvider implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
        
        return apiTemplate.getExRate(url, new HttpClientApiExecutor(), new ErApiExRateExtractor()); // 여기서 사용할 Executor를 선택해서 사용할 수 있다.
    }


}
