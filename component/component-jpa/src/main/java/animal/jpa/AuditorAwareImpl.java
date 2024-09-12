package animal.jpa;

import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

//TODO: Autdit 관련 설정. 토큰 검증 후 헤더에 사용자 정보 가져온 후 사용
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String username = request.getHeader("X-User-Name");

        return Optional.of(Objects.requireNonNullElse("username", "system"));
    }
}