package animal.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import animal.domain.Hub;
import animal.dto.HubResponse.CreateHubRes;
import animal.dto.HubResponse.GetHubRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface HubMapper {

    @Mapping(target = "id", source = "id.id")
    GetHubRes toGetHubRes(Hub hub);

    @Mapping(target = "id", source = "id.id")
    CreateHubRes toCreateHubRes(Hub hub);
}
