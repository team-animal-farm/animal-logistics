package animal.application.notification.infrastructure;

import animal.application.notification.domain.Notification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
