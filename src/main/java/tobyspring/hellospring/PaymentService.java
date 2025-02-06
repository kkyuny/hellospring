package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class PaymentService {
    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }
    /*
       public PaymentService() {
          this.exRateProvider = new WebApiExRateProvider();
       }
       의존관계가 설정됐다고 표현한다.
       클래스를 변경할 때 마다 클래스와 메서드 이름을 계속 변경해야한다.
       인터페이스도 사용하는 메서드의 클래스에 의존한다.

       PaymentService가 관계설정의 책임과 분리 역할을 맡는다.
       Simple과 WebApi 둘 중에서 생성자를 선택해서 사용한다.


     */

    /*
        public PaymentService(ExRateProvider exRateProvider) {
          this.exRateProvider = exRateProvider;
        }
        위와 같이 생성자를 주입받아 사용하면 클라이언트 측에서 관계설정의 책임과 분리 역할을 맡는다.
     */

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        // https://open.er-api.com/v6/latest/USD
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
