package animal.application.order.domain.delivery;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    private Integer estimatedDistance;

    private Integer estimatedTime;

    private Integer actualDistance;

    private Integer actualTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    @Builder
    private DeliveryPath(
        Integer sequence,
        UUID startHubId,
        UUID endHubId,
        Integer estimatedDistance,
        Integer estimatedTime,
        Integer actualDistance,
        Integer actualTime
    ) {
        this.sequence = sequence;
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
    }
}
