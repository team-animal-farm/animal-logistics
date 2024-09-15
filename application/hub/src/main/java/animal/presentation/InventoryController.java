package animal.presentation;

import animal.application.InventoryService;
import animal.domain.HubId;
import animal.domain.InventoryId;
import animal.dto.InventoryRequest.AdjustInventoryReq;
import animal.dto.InventoryRequest.CreateInventoryReq;
import animal.dto.InventoryRequest.UpdateInventoryReq;
import animal.dto.InventoryResponse.AdjustInventoryRes;
import animal.dto.InventoryResponse.CreateInventoryRes;
import animal.dto.InventoryResponse.GetInventoryRes;
import animal.dto.InventoryResponse.UpdateInventoryRes;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;
import response.CommonResponse.CommonEmptyRes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs/{hubId}/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * 재고 단건 조회
     */
    @GetMapping("/{inventoryId}")
    public CommonResponse<GetInventoryRes> getInventory(
        @PathVariable("hubId") UUID hubId,
        @PathVariable("inventoryId") UUID inventoryId
    ) {
        GetInventoryRes response = inventoryService.getInventory(HubId.of(hubId), InventoryId.of(inventoryId));
        return CommonResponse.success(response);
    }

    /**
     * 재고 리스트 조회
     */
    @GetMapping
    public CommonResponse<List<GetInventoryRes>> getInventoryList(@PathVariable("hubId") UUID hubId) {
        List<GetInventoryRes> response = inventoryService.getInventoryList(HubId.of(hubId));
        return CommonResponse.success(response);
    }

    /**
     * 재고 생성
     */
    @PostMapping
    public CommonResponse<CreateInventoryRes> createInventory(
        @PathVariable("hubId") UUID hubId,
        @RequestBody CreateInventoryReq createInventoryReq
    ) {
        CreateInventoryRes response = inventoryService.createInventory(HubId.of(hubId), createInventoryReq);
        return CommonResponse.success(response);
    }

    /**
     * 재고 절대적인 수량 변경
     */
    @PatchMapping("/{inventoryId}")
    public CommonResponse<UpdateInventoryRes> updateInventory(
        @PathVariable("hubId") UUID hubId,
        @PathVariable("inventoryId") UUID inventoryId,
        @RequestBody UpdateInventoryReq updateInventoryReq
    ) {
        UpdateInventoryRes response = inventoryService
            .updateInventory(HubId.of(hubId), InventoryId.of(inventoryId), updateInventoryReq);
        return CommonResponse.success(response);
    }

    /**
     * 여러 재고 수량 변경
     */
    @PatchMapping("/adjust")
    public CommonResponse<List<AdjustInventoryRes>> adjustInventories(
        @PathVariable("hubId") UUID hubId,
        @RequestBody List<AdjustInventoryReq> adjustInventoryListReq
    ) {

        List<AdjustInventoryRes> response = inventoryService.adjustInventoryQuantity(HubId.of(hubId), adjustInventoryListReq);

        return CommonResponse.success(response);
    }

    /**
     * 재고 삭제
     */
    @DeleteMapping("/{inventoryId}")
    public CommonResponse<CommonEmptyRes> deleteInventory(
        @PathVariable("hubId") UUID hubId,
        @PathVariable("inventoryId") UUID inventoryId
    ) {
        inventoryService.deleteInventory(HubId.of(hubId), InventoryId.of(inventoryId));
        return CommonResponse.success();
    }
}
