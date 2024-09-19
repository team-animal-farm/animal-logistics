package animal.application.order.domain.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_slack")
public class Slack {

    @Id
    private final UUID id = UUID.randomUUID();

    private String slackId;

    @Column(length = 1000)
    private String message;

    private LocalDateTime sendTime;
}