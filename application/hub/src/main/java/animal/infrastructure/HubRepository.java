package animal.infrastructure;

import animal.domain.Hub;
import animal.domain.HubId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, HubId> {

}
