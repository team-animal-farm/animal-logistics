package animal.application.order.infrastructure.hub;

import animal.application.order.dto.hub.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "hub-service",
    fallbackFactory = HubFallbackFactory.class
)
@Component
public interface HubClient {

    /**
     * 모든 허브 ID List 조회
     */
    @GetMapping("/hubs/idList")
    HubResponse.GetHubRes getHubIdList();


}
