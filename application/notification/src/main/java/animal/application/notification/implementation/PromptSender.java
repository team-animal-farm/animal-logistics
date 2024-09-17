package animal.application.notification.implementation;

import animal.application.notification.property.AiProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptSender {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final AiProperty aiProperty;
    private final RestTemplate restTemplate;

    /**
     * RestTemplate 이용 API 요청메소드
     */
    public String send(String requestContent) {

        HttpEntity<PromptReq> request = createHttpEntity(requestContent);
        ResponseEntity<String> response = sendToAiApi(request);

        return extractPrompt(response.getBody());
    }

    private HttpEntity<PromptReq> createHttpEntity(String requestContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        PromptReq promptReq = new PromptReq(List.of(new PromptReq.Content(List.of(new PromptReq.Part(requestContent)))));
        return new HttpEntity<>(promptReq, headers);
    }

    private ResponseEntity<String> sendToAiApi(HttpEntity<PromptReq> requestEntity) {

        return restTemplate.exchange(
            aiProperty.getUrlWithKey(),
            HttpMethod.POST,
            requestEntity,
            String.class
        );
    }

    /**
     * API response 에서 text 추출하는 메소드
     */
    private String extractPrompt(String responseBody) {
        try {
            return objectMapper.readTree(responseBody)
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText("");
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * Prompt API 요청 DTO
     */
    private record PromptReq(
        List<Content> contents
    ) {

        private record Content(
            List<Part> parts
        ) {

        }

        private record Part(
            String text
        ) {

        }
    }
}
