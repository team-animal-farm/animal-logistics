package animal.application.hub.dto;

import animal.application.hub.domain.hub.Address;
import animal.application.hub.domain.hub.Coordinate;
import animal.application.hub.dto.ManagerResponse.GetCompanyDeliveryManagerRes;
import animal.application.hub.dto.ManagerResponse.GetHubDeliveryManagerRes;
import animal.application.hub.dto.ManagerResponse.GetHubManagerRes;
import animal.application.hub.dto.ManagerResponse.GetProviderCompanyManagerRes;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public class HubResponse {

    public record GetHubRes(
        UUID id,
        String name,
        Integer sequence,
        Address address,
        Coordinate coordinate,
        List<GetHubManagerRes> hubManagerList,
        List<GetHubDeliveryManagerRes> hubDeliveryManagerList,
        List<GetCompanyDeliveryManagerRes> companyDeliveryManagerList,
        List<GetProviderCompanyManagerRes> providerCompanyManagerList
    ) {

    }

    public record CreateHubRes(
        UUID id,
        Address address,
        Coordinate coordinate,
        List<GetHubManagerRes> hubManagerList,
        List<GetCompanyDeliveryManagerRes> companyDeliveryManagerList,
        List<GetProviderCompanyManagerRes> providerCompanyManagerList
    ) {

    }

    public record UpdateHubRes(
        UUID id,
        Address address,
        Coordinate coordinate,
        List<GetHubManagerRes> hubManagerList,
        List<GetCompanyDeliveryManagerRes> companyDeliveryManagerList,
        List<GetProviderCompanyManagerRes> providerCompanyManagerList
    ) {

    }

    @Builder
    public record GetHubIdList(
        List<UUID> hubIds
    ) {

    }
}
