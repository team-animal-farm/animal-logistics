package animal.application.order.application.order.util;

import java.util.UUID;
import lombok.Getter;

@Getter
public class HubNode {

    private UUID startId;

    private Integer sequence;

    private HubNode rt;

    private HubNode lt;

    private Integer estimatedTime;

    private String estimatedDistance;
}
