package animal.application.order.dto.user;

import java.util.UUID;

public class UserResponse {

    public record GetDeliveryDriver(
        String username,
        UUID hubId,
        String slackId
    ) {

    }
}
