package animal.application.order.dto.slack;

import animal.application.order.domain.delivery.Slack;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlackMessageReq {

    private String slackId;
    private String message;
    private LocalDateTime sendTime;

    public Slack toEntity(LocalDateTime now) {
        return Slack.builder()
            .slackId(slackId)
            .message(message)
            .sendTime(now)
            .build();
    }
}
