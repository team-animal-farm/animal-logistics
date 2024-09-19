package animal.application.order.application;

import animal.application.order.dto.slack.SlackIncomingHookDto;
import animal.application.order.dto.slack.SlackMessageReq;
import animal.application.order.dto.slack.SlackMessageRes;
import animal.application.order.infrastructure.slack.SlackRepository;
import exception.GlobalException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SlackService")
@Transactional(readOnly = true)
public class SlackService {

    private final SlackRepository slackRepository;
    private final RestTemplate slackRestTemplate;

    @Value("${slack.incoming-hook.url}")
    String slackURL;

    @Transactional
    public SlackMessageRes sendSlackMessage(SlackMessageReq slackMessageReq) {
        try {
            SlackIncomingHookDto request = new SlackIncomingHookDto("@" + slackMessageReq.getSlackId(),
                slackMessageReq.getMessage());
            log.info(slackRestTemplate.postForObject(slackURL, request, String.class));
            return SlackMessageRes.from(slackRepository.save(slackMessageReq.toEntity(LocalDateTime.now())));
        } catch (Exception e) {
            log.error("Internal Server Error");
            throw new GlobalException(ErrorCase.SYSTEM_ERROR);
        }
    }


}
