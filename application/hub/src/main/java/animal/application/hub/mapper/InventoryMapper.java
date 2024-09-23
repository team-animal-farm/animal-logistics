package animal.application.hub.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import animal.application.hub.domain.inventory.Inventory;
import animal.application.hub.dto.InventoryResponse.AdjustInventoryRes;
import animal.application.hub.dto.InventoryResponse.CreateInventoryRes;
import animal.application.hub.dto.InventoryResponse.GetInventoryRes;
import animal.application.hub.dto.InventoryResponse.UpdateInventoryRes;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface InventoryMapper {

    @Mapping(target = "id", source = "id.id")
    CreateInventoryRes toCreateInventoryRes(Inventory inventory);

    @Mapping(target = "id", source = "id.id")
    GetInventoryRes toGetInventoryResList(Inventory inventory);

    List<GetInventoryRes> toGetInventoryResList(List<Inventory> inventory);

    @Mapping(target = "id", source = "id.id")
    UpdateInventoryRes toUpdateInventoryRes(Inventory inventory);

    @Mapping(target = "id", source = "id.id")
    AdjustInventoryRes toAdjustInventoryRes(Inventory inventory);
}
