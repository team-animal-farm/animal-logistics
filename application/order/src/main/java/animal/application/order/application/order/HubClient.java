package animal.application.order.application.order;

import animal.application.order.dto.OrderResponse.GetHubIdReq;
import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.dto.OrderResponse.GetNode;
import animal.application.order.dto.OrderResponse.GetProductRes;
import animal.application.order.dto.OrderResponse.OrderProduct;
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

    //업체가 소속되어 있는 허브 조회
    @PostMapping("/hubId")
    GetHubIdRes getHubId(GetHubIdReq dto);

    //재고 감소
    @PostMapping("/{hubId}/inventories/adjust")
    List<GetProductRes> adjustInventories(@PathVariable UUID hubId, List<OrderProduct> products);

    @GetMapping()
    List<GetNode> getHubList();
}
