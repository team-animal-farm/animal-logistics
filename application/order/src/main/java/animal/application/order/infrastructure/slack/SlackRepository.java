package animal.application.order.infrastructure.slack;

import animal.application.order.domain.delivery.Slack;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackRepository extends JpaRepository<Slack, UUID> {

}