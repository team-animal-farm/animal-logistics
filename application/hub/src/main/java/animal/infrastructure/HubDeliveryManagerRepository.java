package animal.infrastructure;

import animal.domain.HubDeliveryManager;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubDeliveryManagerRepository extends JpaRepository<HubDeliveryManager, UUID> {

}
