package animal.application.order.domain.delivery;

import animal.jpa.BaseEntity;
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
@Table(name = "p_hub_path")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubPath extends BaseEntity {

    @Id
    private final UUID id = UUID.randomUUID();

    private UUID endHubId;

    private Integer estimatedTime;

    private String pathName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    @Builder
    private HubPath(
        UUID endHubId,
        Integer estimatedTime,
        String pathName
    ) {
        this.endHubId = endHubId;
        this.estimatedTime = estimatedTime;
        this.pathName = pathName;
    }
}
