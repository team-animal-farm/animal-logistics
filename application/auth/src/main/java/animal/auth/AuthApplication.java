package animal.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import security.JwtUtil;
import security.UserRolePreAuthFilter;

@SpringBootApplication
@Import({JwtUtil.class, UserRolePreAuthFilter.class})
@EnableFeignClients
public class AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }
}
