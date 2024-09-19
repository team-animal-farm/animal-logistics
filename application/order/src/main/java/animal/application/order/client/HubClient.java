package animal.application.order.client;

import animal.application.order.dto.OrderResponse.GetHubIdReq;
import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.dto.OrderResponse.GetNode;
import animal.application.order.dto.OrderResponse.GetProductRes;
import animal.application.order.dto.OrderResponse.HubData;
import animal.application.order.dto.OrderResponse.OrderProduct;
import animal.application.order.dto.hub.HubResponse.GetHubIdListRes;
import animal.application.order.dto.hub.HubResponse.GetHubRes;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import response.CommonResponse;

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

    //업체가 소속되어 있는 허브 조회
    @PostMapping("/hubId")
    GetHubIdRes getHubId(GetHubIdReq dto);

    //재고 감소
    @PostMapping("/{hubId}/inventories/adjust")
    List<GetProductRes> adjustInventories(@PathVariable UUID hubId, List<OrderProduct> products);

    @GetMapping()
    List<GetNode> getHubList();

    /**
     * 모든 허브 ID List 조회
     */
    @GetMapping("/idList")
    GetHubIdListRes getHubIdList();

    /**
     * 허브 ID로 허브 조회
     */
    @GetMapping("/{hubId}")
    CommonResponse<GetHubRes> getHub(@PathVariable("hubId") UUID hubId);
}
