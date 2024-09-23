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
public class HubManager {

    @Embedded
    private Username username;

    @Builder
    private HubManager(Username username) {
        this.username = username;
    }
}
