package animal.auth.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HubFallbackFactory implements FallbackFactory<HubClient> {


  @Override
  public HubClient create(Throwable cause) {

    log.info("fallback due to: " + cause.getMessage(), cause);

  }
}
