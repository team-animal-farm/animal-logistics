package animal.auth.config;

import feign.Retryer;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

  //재시도 로직
  @Bean
  public Retryer retryer() {
    return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 2);
  }

/*  @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("API-KEY", "123");
            requestTemplate.header("USER-ROLE", "CUSTOMER");
            requestTemplate.header("SERVER-PORT", serverPort);
        };
    }*/
}
