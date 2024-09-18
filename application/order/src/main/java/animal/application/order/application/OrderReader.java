package animal.application.order.application;

import animal.application.order.domain.order.Order;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderReader {

    List<Order> getWaitingOrderList() {
        // TODO : 배송 대기인 주문 조회
        return List.of();
    }
}
