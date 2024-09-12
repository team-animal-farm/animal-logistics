package animal.domain.manager;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SlackId {

    private String slackId;

    public static SlackId of(String slackId) {
        SlackId id = new SlackId();
        id.slackId = slackId;
        return id;
    }

    @Override
    public String toString() {
        return slackId;
    }
}
