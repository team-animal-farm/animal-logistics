package animal.application.hub.domain.hub;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 허브의 id 값객체
 */
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubId implements Serializable { // JPA 식별자 타입은 Serializable 구현해야 함

    @Column(name = "id")
    private UUID id;

    public static HubId of(UUID id) {
        HubId hubId = new HubId();
        hubId.id = id;
        return hubId;
    }

    public static HubId ofRandom() {
        HubId hubId = new HubId();
        hubId.id = UUID.randomUUID();
        return hubId;
    }
}
