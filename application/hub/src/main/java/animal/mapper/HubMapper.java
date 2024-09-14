package animal.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import animal.domain.Hub;
import animal.domain.manager.CompanyDeliveryManager;
import animal.domain.manager.HubDeliveryManager;
import animal.domain.manager.HubManager;
import animal.domain.manager.ProviderCompanyManager;
import animal.dto.HubRequest.CreateHubReq;
import animal.dto.HubResponse.CreateHubRes;
import animal.dto.HubResponse.GetHubRes;
import animal.dto.HubResponse.UpdateHubRes;
import animal.dto.ManagerResponse.GetCompanyDeliveryManagerRes;
import animal.dto.ManagerResponse.GetHubDeliveryManagerRes;
import animal.dto.ManagerResponse.GetHubManagerRes;
import animal.dto.ManagerResponse.GetProviderCompanyManagerRes;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface HubMapper {

    Hub toHub(CreateHubReq createHubReq);

    @Mapping(target = "id", source = "id.id")
    GetHubRes toGetHubRes(Hub hub);

    List<GetHubRes> toGetHubResList(List<Hub> hubList);

    @Mapping(target = "id", source = "id.id")
    CreateHubRes toCreateHubRes(Hub hub);

    @Mapping(target = "id", source = "id.id")
    UpdateHubRes toUpdateHubRes(Hub hub);
    
    @Mapping(target = "username", source = "username.username")
    GetHubManagerRes toGetHubManagerRes(HubManager hubManager);

    @Mapping(target = "username", source = "username.username")
    @Mapping(target = "slackId", source = "slackId.slackId")
    GetHubDeliveryManagerRes toGetHubDeliveryManagerRes(HubDeliveryManager hubDeliveryManager);

    @Mapping(target = "username", source = "username.username")
    @Mapping(target = "slackId", source = "slackId.slackId")
    GetCompanyDeliveryManagerRes toGetCompanyDeliveryManagerRes(CompanyDeliveryManager companyDeliveryManager);

    @Mapping(target = "username", source = "username.username")
    @Mapping(target = "slackId", source = "slackId.slackId")
    GetProviderCompanyManagerRes toGetProviderCompanyManagerRes(ProviderCompanyManager providerCompanyManager);
}
