package animal.application.order.application;

import animal.application.order.domain.delivery.Delivery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryReader {

    List<Delivery> getDeliveryOfWaitingOrderList() {
        // TODO : 배송 대기 주문의 배송 엔티티 조회
        return List.of();
    }
}
