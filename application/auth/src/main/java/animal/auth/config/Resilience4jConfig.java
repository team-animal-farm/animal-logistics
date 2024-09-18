package animal.auth.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4jConfig {

  @Bean
  public CircuitBreakerConfig circuitBreakerConfig() {
    return CircuitBreakerConfig.custom()
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)  // 슬라이딩 윈도우 타입 설정
        .slidingWindowSize(5)  // 슬라이딩 윈도우 크기 설정
        .minimumNumberOfCalls(5)  // 최소 호출 수 설정
        .slowCallRateThreshold(100)  // 느린 호출 비율 임계값 설정
        .slowCallDurationThreshold(Duration.ofMillis(60000))  // 느린 호출 기준 시간 설정
        .failureRateThreshold(50)  // 실패율 임계값 설정
        .permittedNumberOfCallsInHalfOpenState(3)  // Half-open 상태에서 허용되는 최대 호출 수 설정
        .waitDurationInOpenState(Duration.ofSeconds(20))  // Open 상태에서 Half-open 상태로 전환되기 전 대기 시간 설정
        .build();
  }

  @Bean
  public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfig circuitBreakerConfig) {
    return CircuitBreakerRegistry.of(circuitBreakerConfig);
  }
}
