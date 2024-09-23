package animal.application.hub.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import animal.application.hub.domain.hub.Hub;
import animal.application.hub.domain.manager.HubDeliveryManager;
import animal.application.hub.domain.manager.CompanyDeliveryManager;
import animal.application.hub.domain.manager.HubManager;
import animal.application.hub.domain.manager.ProviderCompanyManager;
import animal.application.hub.dto.HubRequest.CreateHubReq;
import animal.application.hub.dto.HubResponse.CreateHubRes;
import animal.application.hub.dto.HubResponse.GetHubRes;
import animal.application.hub.dto.HubResponse.UpdateHubRes;
import animal.application.hub.dto.ManagerResponse.GetCompanyDeliveryManagerRes;
import animal.application.hub.dto.ManagerResponse.GetHubDeliveryManagerRes;
import animal.application.hub.dto.ManagerResponse.GetHubManagerRes;
import animal.application.hub.dto.ManagerResponse.GetProviderCompanyManagerRes;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface HubMapper {

    Hub toHub(CreateHubReq createHubReq);

    @Mapping(target = "id", source = "hub.id.id")
    GetHubRes toGetHubRes(Hub hub, List<HubDeliveryManager> hubDeliveryManagerList);

    @Mapping(target = "id", source = "id.id")
    CreateHubRes toCreateHubRes(Hub hub);

    @Mapping(target = "id", source = "id.id")
    UpdateHubRes toUpdateHubRes(Hub hub);

    @Mapping(target = "username", source = "username.username")
    GetHubManagerRes toGetHubManagerRes(HubManager hubManager);

    GetHubDeliveryManagerRes toGetHubDeliveryManagerRes(HubDeliveryManager hubDeliveryManager);

    List<GetHubDeliveryManagerRes> toGetHubDeliveryManagerResList(List<HubDeliveryManager> hubDeliveryManagerList);

    @Mapping(target = "username", source = "username.username")
    @Mapping(target = "slackId", source = "slackId.slackId")
    GetCompanyDeliveryManagerRes toGetCompanyDeliveryManagerRes(CompanyDeliveryManager companyDeliveryManager);

    @Mapping(target = "username", source = "username.username")
    @Mapping(target = "slackId", source = "slackId.slackId")
    GetProviderCompanyManagerRes toGetProviderCompanyManagerRes(ProviderCompanyManager providerCompanyManager);
}
