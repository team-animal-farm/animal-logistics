package animal.application.order.application.order.util;

import java.util.UUID;

public class HubNode {

    private UUID startId;

    private HubNode rt;

    private HubNode lt;

    private Integer estimatedTime;

    private String estimatedDistance;
}
