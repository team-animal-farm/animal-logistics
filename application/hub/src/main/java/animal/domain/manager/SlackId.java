package animal.domain.manager;

import exception.GlobalException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import response.ErrorCase;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SlackId {

    private String slackId;

    public static SlackId of(String slackId) {
        if (!StringUtils.hasText(slackId)) {
            throw new GlobalException(ErrorCase.INVALID_INPUT);
        }

        SlackId id = new SlackId();
        id.slackId = slackId;
        return id;
    }

    @Override
    public String toString() {
        return slackId;
    }
}
