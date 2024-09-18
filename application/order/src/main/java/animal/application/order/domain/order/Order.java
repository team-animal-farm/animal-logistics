package animal.application.order.domain.order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    private final UUID id = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    private Order Order(OrderStatus status) {

        Order order = new Order();
        order.status = status;

        return order;
    }
}
