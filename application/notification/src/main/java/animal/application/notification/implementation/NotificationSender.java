package animal.application.notification.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSender {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate;

    public void send(String slackId, String message) {
        // TODO: 슬랙에 전송
    }
}
