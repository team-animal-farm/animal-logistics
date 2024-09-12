package animal.application;

import animal.domain.Hub;
import animal.domain.HubId;
import animal.domain.Inventory;
import animal.domain.InventoryId;
import animal.dto.InventoryRequest.CreateInventoryReq;
import animal.dto.InventoryRequest.UpdateInventoryReq;
import animal.dto.InventoryResponse.AdjustInventoryRes;
import animal.dto.InventoryResponse.CreateInventoryRes;
import animal.dto.InventoryResponse.GetInventoryRes;
import animal.dto.InventoryResponse.UpdateInventoryRes;
import animal.infrastructure.HubRepository;
import animal.mapper.InventoryMapper;
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
     * 재고의 상대적인 수량 수정
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE) // TODO : 추후 최적화 필요
    public AdjustInventoryRes adjustInventoryQuantity(HubId hubId, InventoryId inventoryId, int quantity) {

        Inventory inventory = findInventory(inventoryId, hubId);
        inventory.adjust(quantity);

        return inventoryMapper.toAdjustInventoryRes(inventory);
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
            .orElseThrow(() -> new GlobalException(ErrorCase.NOT_FOUND));
    }

    private Inventory findInventory(InventoryId inventoryId, HubId hubId) {
        Hub hub = findHub(hubId);

        return hub.getInventory(inventoryId)
            .orElseThrow(() -> new GlobalException(ErrorCase.NOT_FOUND));
    }
}
