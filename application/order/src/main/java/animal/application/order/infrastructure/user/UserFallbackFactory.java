package animal.application.order.infrastructure.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFallbackFactory implements FallbackFactory<UserClient> {


    @Override
    public UserClient create(Throwable cause) {

        log.info("fallback due to: " + cause.getMessage(), cause);

        return null;
    }
}
