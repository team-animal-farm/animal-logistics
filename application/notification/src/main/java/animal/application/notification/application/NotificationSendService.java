package animal.application.notification.application;

import animal.application.notification.domain.Platform;
import animal.application.notification.domain.Prompt;
import animal.application.notification.implementation.NotificationSaver;
import animal.application.notification.implementation.NotificationSender;
import animal.application.notification.implementation.PromptCreator;
import animal.application.notification.implementation.PromptSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSendService {

    private final PromptSender promptSender;
    private final PromptCreator promptCreator;
    private final NotificationSaver notificationSaver;
    private final NotificationSender notificationSender;

    /**
     * 메시지 전송
     */
    @Transactional
    public void sendNotification(String recipientId, String requestMessage) {

        String responseMessage = promptSender.send(requestMessage);
        notificationSender.send(recipientId, responseMessage);

        Prompt prompt = promptCreator.create(requestMessage, responseMessage);
        notificationSaver.save(recipientId, Platform.SLACK, prompt);
    }
}
