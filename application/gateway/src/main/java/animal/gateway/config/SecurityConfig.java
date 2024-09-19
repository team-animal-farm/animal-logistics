package animal.gateway.config;

import animal.gateway.filter.PreFilter;
import io.jsonwebtoken.Claims;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import security.JwtUtil;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final Logger logger = Logger.getLogger(PreFilter.class.getName());
  private final JwtUtil jwtUtil;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .csrf(ServerHttpSecurity.CsrfSpec::disable) // CSRF 비활성화
        .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC);

    return http.build();
  }

  @Bean
  public WebFilter jwtAuthenticationFilter() {
    // TODO: 게이트웨이 jwt 인증 처리 필터
    return (exchange, chain) -> {

      // /auth/login 경로는 필터를 적용하지 않음

      String path = exchange.getRequest().getURI().getPath();

      logger.info("Pre filter :Request URI is" + path);

      //회원가입과 로그인 시 토큰 검증 하지 않음.
      if (path.startsWith("/auth")) {
        return chain.filter(exchange);
      }
      //토큰 추출
      String token = jwtUtil.parseToken(exchange);

      // 토큰 유효성 검증과 만료기간 확인
      Claims claims = jwtUtil.tokenParseClaims(token);

      //헤더에 값을 추가
      ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
          .header("X-User-Name", jwtUtil.getUsername(claims))
          .header("X-User-Roles", jwtUtil.getRole(claims))
          .build();

      // 수정된 요청으로 필터 체인 계속 처리
      ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
      return chain.filter(modifiedExchange);

    };
  }
}