package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

@Component // 클라이언트에서 등록 된 컴포턴트를 이용해서 의존관계를 설정한다.
public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String collect;
        try {
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            try ( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                collect = bufferedReader.lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            ExRateData exRateData = mapper.readValue(collect, ExRateData.class);

            return exRateData.rates().get("KRW");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
