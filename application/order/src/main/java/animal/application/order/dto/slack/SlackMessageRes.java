package animal.application.order.dto.slack;

import animal.application.order.domain.delivery.Slack;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public class SlackMessageRes {

    private UUID id;
    private String receiverId;
    private String message;
    private LocalDateTime sendTime;

    public static SlackMessageRes from(Slack slack) {
        return SlackMessageRes.builder()
            .id(slack.getId())
            .receiverId(slack.getSlackId())
            .message(slack.getMessage())
            .sendTime(slack.getSendTime())
            .build();
    }
}
