package animal.application.order.infrastructure.user;

import animal.application.order.dto.user.UserResponse.GetDeliveryDriver;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import response.UserRole;

@FeignClient(
    name = "auth-service",
    fallbackFactory = UserFallbackFactory.class
)
@Component
public interface UserClient {

    /**
     * 허브 ID로 업체 배송기사 조회
     *
     * @param hubId    허브 ID
     * @param userRole DELIVERY_COMPANY
     */
    @GetMapping("/users/{hubId}")
    List<GetDeliveryDriver> getDeliveryDriver(@PathVariable UUID hubId, @RequestParam UserRole userRole);


}

