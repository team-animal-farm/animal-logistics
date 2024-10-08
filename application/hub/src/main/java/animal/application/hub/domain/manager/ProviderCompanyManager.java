package animal.application.hub.domain.manager;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProviderCompanyManager {

    @Embedded
    private Username username;

    @Embedded
    private SlackId slackId;

    private UUID companyId;

    @Builder
    private ProviderCompanyManager(Username username, SlackId slackId, UUID companyId) {
        this.username = username;
        this.slackId = slackId;
        this.companyId = companyId;
    }
}
