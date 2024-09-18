package animal.application.order.domain;

import animal.jpa.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import response.DeliveryStatus;

@Entity
@Getter
@Table(name = "p_delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @EmbeddedId
    private final DeliveryId id = new DeliveryId();

    private UUID orderId;
    private UUID start_id;
    private UUID destination_id;
    private UUID hubId;
    private DeliveryStatus deliveryStatus;
    private Address address;
    private String recipient;
    private String recipientSlackId;
    private UUID driverId;

    public void updateDriver(String username) {
    }
}
