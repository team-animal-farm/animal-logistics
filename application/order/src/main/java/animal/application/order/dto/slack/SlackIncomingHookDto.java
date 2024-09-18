package animal.application.order.dto.slack;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SlackIncomingHookDto {

    private String username;
    private String text;
}
