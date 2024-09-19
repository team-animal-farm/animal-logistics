package animal.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//@Configuration
public class GatewayConfig {

  @Bean
  public RouteLocator customRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("auth-service", r -> r.path("/users/**", "/auth/**")
            .uri("lb://auth-service")
        )
        .route("company-service", r -> r.path("/companies/**")
            .uri("lb://company-service")
        )
        .route("hub-service", r -> r.path("/hubs/**")
            .uri("lb://hub-service")
        )
        .route("notification-service", r -> r.path("/notifications/**")
            .uri("lb://notification-service")
        )
        .route("order-service", r -> r.path("/orders/**")
            .uri("lb://order-service")
        )
        .build();
  }
}
