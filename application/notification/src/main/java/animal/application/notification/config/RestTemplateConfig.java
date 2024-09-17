package animal.application.notification.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final int CONNECTION_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            // RestTemplate 으로 외부 API 호출 시 일정 시간이 지나도 응답이 없을 때
            // 무한 대기 상태 방지를 위해 강제 종료 설정
            .setConnectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
            .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT))
            .build();
    }
}
