package animal.application.order.client;

import animal.application.order.dto.OrderResponse.HubData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "hub-service",
    path = "/hubs"
)
public interface HubClient {

    /**
     * 허브 리스트 조회
     */
    @GetMapping
    HubData getHubs();
}
