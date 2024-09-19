package animal.application.order.domain.delivery;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubDeliveryManager {

    private String username;

    @Embedded
    private String slackId;

    @Builder
    private HubDeliveryManager(String username, String slackId) {
        this.username = username;
        this.slackId = slackId;
    }
}
