package animal.application.hub.infrastructure;

import animal.application.hub.domain.manager.HubDeliveryManager;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubDeliveryManagerRepository extends JpaRepository<HubDeliveryManager, UUID> {

}
