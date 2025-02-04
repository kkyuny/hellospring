package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService(new SimpleExRateProvider()); // 클라이언트에서 관계책임을 설정한다.
        /*
        * 1. 생성자를 생성한다.
        * 2. 서비스에 생성자를 전달 및 사용한다.
        * 3. 서비스에서는 생성자를 전달 받아 인터페이스의 메서드를 실행한다.
        * */
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
