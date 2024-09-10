package animal.utils;

import animal.domain.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${jwt.secret.access-expiration}")
    private long accessExpiration; // 60분

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }
    private final SecretKey secretKey;

    public String createToken(String email, UserRole role) {
        return BEARER_PREFIX + Jwts.builder()
                .claim("email", email)
                .claim("role", role.name())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    //토큰 유효성 검증
    /*public Claims validateToken(String token) {

    }*/

    // calims  만료 기간 검증
    /*public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }*/

    //jwt 추출
    public String parseJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //claims 추출
    public Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}