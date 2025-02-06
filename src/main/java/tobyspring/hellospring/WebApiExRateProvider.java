package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component // 클라이언트에서 해당 컴포턴트를 이용해서 의존관계를 설정한다.
public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String collect = bufferedReader.lines().collect(Collectors.joining());
        bufferedReader.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData exRateData = mapper.readValue(collect, ExRateData.class);

        return exRateData.rates().get("KRW");
    }
}
