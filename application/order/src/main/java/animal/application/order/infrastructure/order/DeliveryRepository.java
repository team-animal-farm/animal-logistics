package animal.application.order.infrastructure.order;

import animal.application.order.domain.delivery.Delivery;
import animal.application.order.domain.delivery.DeliveryStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    List<Delivery> findByEndHubIdAndStatus(UUID hubId, DeliveryStatus deliveryStatus);
}
