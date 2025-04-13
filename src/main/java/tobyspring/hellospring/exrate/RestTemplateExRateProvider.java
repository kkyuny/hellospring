package tobyspring.hellospring.exrate;

import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;

public class RestTemplateExRateProvider implements ExRateProvider {
    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return restTemplate.getForObject(url, ExRateData.class).rates().get("KRW"); // JSON 형태로 응답이 오면 ExRateData로 자동 바인딩 된다.
    }
}
