package animal.application.order.application;

import animal.application.order.domain.delivery.Delivery;
import animal.application.order.domain.delivery.DeliveryStatus;
import animal.application.order.infrastructure.order.DeliveryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryReader {

    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getDeliveryOfWaitingOrderList() {

        return deliveryRepository.findAllByStatus(DeliveryStatus.WAITING_AT_HUB);
    }
}
