package animal.application.order.infrastructure;

import animal.application.order.domain.delivery.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

}
