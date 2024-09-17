package animal.application.order.infrastructure.order;

import animal.application.order.domain.Delivery;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import response.DeliveryStatus;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    List<Delivery> findByHubIdAndDeliveryStatus(UUID hubId, DeliveryStatus deliveryStatus);
}
