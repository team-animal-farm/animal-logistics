package animal.application.order.application;

import animal.application.order.dto.gemini.GeminiReq;
import animal.application.order.dto.gemini.GeminiRes;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "GeminiService")
@Transactional(readOnly = true)
public class GeminiService {

    private final RestTemplate geminiRestTemplate;

    @Value("${gemini.api.key}")
    String geminiApiKey;

    //    @Cacheable(cacheNames = "WeatherSummary", key = "args[1]")
    public String getGeminiSummary(String requestPrompt, String date) {
        String geminiURL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="
            + geminiApiKey;

        GeminiReq request = new GeminiReq();

        request.createGeminiReqDto(requestPrompt);
        String result = "";

        try {
            GeminiRes response = geminiRestTemplate.postForObject(geminiURL, request, GeminiRes.class);
            log.info("Gemini 요청 성공");
            result = response.getCandidates().get(0).content().parts().get(0).text();
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("INTERNAL SERVER ERROR");
            throw new GlobalException(ErrorCase.SYSTEM_ERROR);
        }

        return result;
    }

}