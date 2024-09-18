package animal.application.order.infrastructure.order;

import animal.application.order.domain.Delivery;
import animal.application.order.domain.DeliveryId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import response.DeliveryStatus;

public interface DeliveryRepository extends JpaRepository<Delivery, DeliveryId> {

    List<Delivery> findByHubIdAndDeliveryStatus(UUID hubId, DeliveryStatus deliveryStatus);
}
