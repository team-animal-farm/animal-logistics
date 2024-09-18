package animal.application.order.domain.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryId implements Serializable { // JPA 식별자 타입은 Serializable 구현해야 함

    @Column(name = "delivery_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public static DeliveryId of(UUID id) {
        DeliveryId deliveryId = new DeliveryId();
        deliveryId.id = id;
        return deliveryId;
    }

    public static DeliveryId ofRandom() {
        DeliveryId deliveryId = new DeliveryId();
        deliveryId.id = UUID.randomUUID();
        return deliveryId;
    }
}