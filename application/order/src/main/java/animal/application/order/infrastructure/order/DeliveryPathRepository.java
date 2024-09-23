package animal.application.order.infrastructure.order;

import animal.application.order.domain.delivery.DeliveryPath;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPathRepository extends JpaRepository<DeliveryPath, UUID> {

}
