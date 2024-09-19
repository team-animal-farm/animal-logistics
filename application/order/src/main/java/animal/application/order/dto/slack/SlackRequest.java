package animal.application.order.dto.slack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlackRequest {

    private String channel;
    private String username;
    private String text;
}
