package animal.application.order.client;

import animal.application.order.dto.OrderResponse.GetHubIdReq;
import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.dto.OrderResponse.GetProductRes;
import animal.application.order.dto.OrderResponse.HubData;
import animal.application.order.dto.OrderResponse.OrderProduct;
import animal.application.order.dto.hub.HubResponse.GetHubRes;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    //hub의 상품 존재 확인하고 재고 감소
    @PostMapping("/adjust/{hubId}")
    List<GetProductRes> adjustInventories(@PathVariable UUID hubId, List<OrderProduct> products);

    @GetMapping("/hubId")
    GetHubIdRes getHubID(GetHubIdReq dto);

    /**
     * 모든 허브 ID List 조회
     */
    @GetMapping("/idList")
    GetHubRes getHubIdList();


}
