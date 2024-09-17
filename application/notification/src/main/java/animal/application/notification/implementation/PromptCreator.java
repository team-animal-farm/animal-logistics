package animal.application.notification.implementation;

import animal.application.notification.domain.Prompt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PromptCreator {

    public Prompt create(String requestContent, String responseContent) {

        return Prompt.builder()
            .requestContent(requestContent)
            .responseContent(responseContent)
            .build();
    }
}
