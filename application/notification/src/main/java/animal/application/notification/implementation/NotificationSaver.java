package animal.application.notification.implementation;

import animal.application.notification.domain.Notification;
import animal.application.notification.domain.Platform;
import animal.application.notification.domain.Prompt;
import animal.application.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSaver {

    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification save(String message, Platform platform, Prompt prompt) {

        Notification notification = Notification.builder()
            .message(message)
            .platform(platform)
            .prompt(prompt)
            .build();

        return notificationRepository.save(notification);
    }
}
