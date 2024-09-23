package animal.application.hub.domain.manager;

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
public class CompanyDeliveryManager {

    @Embedded
    private Username username;

    @Embedded
    private SlackId slackId;

    @Builder
    private CompanyDeliveryManager(Username username, SlackId slackId) {
        this.username = username;
        this.slackId = slackId;
    }
}
