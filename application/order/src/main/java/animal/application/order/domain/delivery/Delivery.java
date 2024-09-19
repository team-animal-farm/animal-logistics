package animal.application.order.domain.delivery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private UUID startHubId;

    @Column(nullable = false)
    private UUID endHubId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private Address address;

    @Column(nullable = false, length = 100)
    private String recipient;

    @Column(nullable = false, length = 100)
    private String recipientSlackId;

    private String hubDeliveryManager;

    private String deliveryManager;

    @OneToOne(mappedBy = "delivery", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private DeliveryPath deliveryPath;

    @Builder
    private Delivery(
        UUID startHubId,
        UUID endHubId,
        DeliveryStatus status,
        Address address,
        String recipient,
        String recipientSlackId,
        String deliveryManager
    ) {
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.status = status;
        this.address = address;
        this.recipient = recipient;
        this.recipientSlackId = recipientSlackId;
        this.deliveryManager = deliveryManager;
    }

    public void updateDeliveryPath(DeliveryPath deliveryPath) {
        this.deliveryPath = deliveryPath;
    }

    public void updateHubDeliveryManager(String hubDeliveryManager) {
        this.hubDeliveryManager = hubDeliveryManager;
    }

    public void updateDeliveryStatus(DeliveryStatus status) {
        this.status = status;
    }
}
