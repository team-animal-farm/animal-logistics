package animal.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityPermission {

    public boolean isResourceOwner(Authentication authentication, String username) {
        log.info("###SecurityPermission 요청이 리소스 소유자임을 확인");
        boolean result = authentication.getName().equals(username);
        log.info("### 확인 완료 : " + result);
        return result;
    }

}
