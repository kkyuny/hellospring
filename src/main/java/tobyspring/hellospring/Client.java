package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        // 클래스들의 의존관계를 외부에서 주입해준 것이다.

        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("1: " + payment1);

        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("2: " + payment1);

        TimeUnit.SECONDS.sleep(3);

        Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("3: " + payment1);
    }
}
