package animal.application.order.dto.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SlackIncomingHookDto {

    private String channel;
    private String text;
}
