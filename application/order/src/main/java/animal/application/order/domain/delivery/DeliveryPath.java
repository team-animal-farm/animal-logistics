package animal.application.order.domain.delivery;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "p_delivery_path")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryPath {

    @Id
    private final UUID id = UUID.randomUUID();

    private Integer sequence;

    private UUID startHubId;

    private UUID endHubId;

    private Double estimatedDistance;

    private Integer estimatedTime;

    private Double actualDistance;

    private Integer actualTime;

    // 양방향 연관관계 설정 (주석 제거)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id") // 외래 키를 저장할 컬럼
    private Delivery delivery;

    @Builder
    private DeliveryPath(
        Integer sequence,
        UUID startHubId,
        UUID endHubId,
        Double estimatedDistance,
        Integer estimatedTime,
        Double actualDistance,
        Integer actualTime,
        Delivery delivery // 추가
    ) {
        this.sequence = sequence;
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
        this.delivery = delivery; // 추가
    }

    public void setDelivery(Delivery d) {
        this.delivery = d;
    }
}

