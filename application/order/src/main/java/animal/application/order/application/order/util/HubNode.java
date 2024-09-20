package animal.application.order.application.order.util;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HubNode {

    private UUID startId;

    private Integer sequence;

    private HubNode rt;

    private HubNode lt;

    private Integer estimatedTime;

    private Integer estimatedDistance;

    public HubNode(UUID uuid, Integer seq, Integer newEstimatedTime, Integer newEstimatedDistance) {
        this.startId = uuid;
        this.sequence = seq;
        this.estimatedDistance = newEstimatedDistance;
        this.estimatedTime = newEstimatedTime;
    }
}
