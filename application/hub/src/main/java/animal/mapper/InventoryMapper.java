package animal.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import animal.domain.Inventory;
import animal.dto.InventoryResponse.AdjustInventoryRes;
import animal.dto.InventoryResponse.CreateInventoryRes;
import animal.dto.InventoryResponse.GetInventoryRes;
import animal.dto.InventoryResponse.UpdateInventoryRes;
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
