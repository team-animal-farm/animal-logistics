package animal.application.order.dto.hub;

import java.util.List;
import java.util.UUID;

public class HubResponse {

    public record GetHubRes(
        List<UUID> hubIds
    ) {

    }

}
