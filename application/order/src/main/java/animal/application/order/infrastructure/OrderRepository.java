package animal.application.order.infrastructure;

import animal.application.order.domain.order.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {


}
