package animal.application.order.domain.delivery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(nullable = true, length = 100)
    private String recipient;

    @Column(length = 100)
    private String recipientSlackId;

    private String hubDeliveryManager;

    private String deliveryManager;

    // Delivery가 연관관계 주체가 되도록 수정
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "delivery_path_id") // 외래 키가 저장될 컬럼
    private DeliveryPath deliveryPath;

    @Builder
    private Delivery(
        UUID startHubId,
        UUID endHubId,
        Address address,
        String recipient,
        String recipientSlackId,
        DeliveryPath deliveryPath,
        String deliveryManager
    ) {
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.status = DeliveryStatus.WAITING_AT_HUB;
        this.address = address;
        this.recipient = recipient;
        this.recipientSlackId = recipientSlackId;
        this.deliveryPath = deliveryPath;
        this.deliveryManager = deliveryManager;
    }

    public void updateDeliveryPath(DeliveryPath deliveryPath) {
        this.deliveryPath = deliveryPath;
        deliveryPath.setDelivery(this); // 연관관계 편의 메소드 추가
    }

    public void updateHubDeliveryManager(String hubDeliveryManager) {
        this.hubDeliveryManager = hubDeliveryManager;
    }

    public void updateDeliveryStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void updateDriver(String username) {
        this.deliveryManager = username;
        this.status = DeliveryStatus.DELIVERING;
    }
}

