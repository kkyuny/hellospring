package tobyspring.hellospring.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.OrderConfig;
import tobyspring.hellospring.TestPaymentConfig;
import tobyspring.hellospring.payment.ExRateProviderStub;
import tobyspring.hellospring.payment.Payment;
import tobyspring.hellospring.payment.PaymentService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
class OrderServiceSpringTest {
    // 스프링 테스트의 장점은 컨피그가 만든 빈을 가져와서 사용할 수 있다는 장점이 있다.
    @Autowired
    OrderService orderService;

    @Autowired
    DataSource dataSource;

    @Test
    void createOrder(){
        var order = orderService.createOrder("0100", BigDecimal.ONE);

        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
    void createOrders() {
        List<OrderReq> reqs = List.of(new OrderReq("0200", BigDecimal.ONE),
                new OrderReq("0201", BigDecimal.ONE)
        );

        var orders = orderService.createOrders(reqs);

        Assertions.assertThat(orders).hasSize(2);
        orders.forEach(order -> {
            Assertions.assertThat(order.getId()).isGreaterThan(0);
        });
    }

    @Test
    void createDuplicateOrders() {
        List<OrderReq> reqs = List.of(
                new OrderReq("0300", BigDecimal.ONE),
                new OrderReq("0300", BigDecimal.ONE)
        );

        assertThatThrownBy(() -> orderService.createOrders(reqs))
                .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);

        var count = client.sql("select count(*) from orders where no = '0300'").query(Long.class).single();
        Assertions.assertThat(count).isEqualTo(0);
    }
}