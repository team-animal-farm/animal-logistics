package animal.application.hub.application;

import animal.application.hub.domain.hub.Hub;
import animal.application.hub.domain.hub.HubId;
import animal.application.hub.domain.inventory.Inventory;
import animal.application.hub.domain.inventory.InventoryId;
import animal.application.hub.dto.InventoryRequest.AdjustInventoryReq;
import animal.application.hub.dto.InventoryRequest.CreateInventoryReq;
import animal.application.hub.dto.InventoryRequest.UpdateInventoryReq;
import animal.application.hub.dto.InventoryResponse.AdjustInventoryRes;
import animal.application.hub.dto.InventoryResponse.CreateInventoryRes;
import animal.application.hub.dto.InventoryResponse.GetInventoryRes;
import animal.application.hub.dto.InventoryResponse.UpdateInventoryRes;
import animal.application.hub.infrastructure.HubRepository;
import animal.application.hub.mapper.InventoryMapper;
import exception.GlobalException;
import jakarta.persistence.LockModeType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryMapper inventoryMapper;
    private final HubRepository hubRepository;

    /**
     * 재고 단건 조회
     */
    public GetInventoryRes getInventory(HubId hubId, InventoryId inventoryId) {

        Inventory inventory = findInventory(inventoryId, hubId);
        return inventoryMapper.toGetInventoryResList(inventory);
    }

    /**
     * 재고 리스트 조회
     */
    public List<GetInventoryRes> getInventoryList(HubId hubId) {

        Hub hub = findHub(hubId);
        List<Inventory> inventoryList = hub.getInventoryList();
        // TODO : 페이징 처리 필요

        return inventoryMapper.toGetInventoryResList(inventoryList);
    }

    /**
     * 재고 생성
     */
    @Transactional
    public CreateInventoryRes createInventory(HubId hubId, CreateInventoryReq createInventoryReq) {

        Hub hub = findHub(hubId);

        Inventory inventory = Inventory.builder()
            .id(InventoryId.of(createInventoryReq.productId()))
            .hub(hub)
            .quantity(createInventoryReq.quantity())
            .price(createInventoryReq.price())
            .build();

        hub.addInventory(inventory);

        return inventoryMapper.toCreateInventoryRes(inventory);
    }

    /**
     * 재고의 절대적인 수정
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE) // TODO : 추후 최적화 필요
    public UpdateInventoryRes updateInventory(HubId hubId, InventoryId inventoryId, UpdateInventoryReq updateInventoryReq) {

        Inventory inventory = findInventory(inventoryId, hubId);
        inventory.update(updateInventoryReq.quantity(), updateInventoryReq.price());

        return inventoryMapper.toUpdateInventoryRes(inventory);
    }

    /**
     * 여러 개의 재고의 상대적인 수량 수정
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE) // TODO : 추후 최적화 필요
    public List<AdjustInventoryRes> adjustInventoryQuantity(HubId hubId, List<AdjustInventoryReq> adjustInventoryReqList) {

        Hub hub = findHub(hubId);

        return adjustInventoryReqList
            .stream()
            .map(request -> adjustInventory(request, hub))
            .map(inventoryMapper::toAdjustInventoryRes)
            .toList();
    }

    private Inventory adjustInventory(AdjustInventoryReq request, Hub hub) {
        Inventory inventory = findInventory(InventoryId.of(request.productId()), hub);
        inventory.adjust(request.quantity());
        return inventory;
    }

    /**
     * 재고 삭제
     */
    @Transactional
    public void deleteInventory(HubId hubId, InventoryId inventoryId) {
        Hub hub = findHub(hubId);
        hub.removeInventory(inventoryId);
    }

    private Hub findHub(HubId hubId) {
        return hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ErrorCase.HUB_NOT_FOUND));
    }

    private Inventory findInventory(InventoryId inventoryId, Hub hub) {
        return hub.getInventory(inventoryId)
            .orElseThrow(() -> new GlobalException(ErrorCase.INVENTORY_NOT_FOUND));
    }

    private Inventory findInventory(InventoryId inventoryId, HubId hubId) {
        Hub hub = findHub(hubId);

        return hub.getInventory(inventoryId)
            .orElseThrow(() -> new GlobalException(ErrorCase.INVENTORY_NOT_FOUND));
    }
}
