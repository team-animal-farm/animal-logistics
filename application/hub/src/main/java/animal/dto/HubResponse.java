package animal.dto;

import animal.domain.Address;
import animal.domain.Coordinate;
import animal.dto.ManagerResponse.GetCompanyDeliveryManagerRes;
import animal.dto.ManagerResponse.GetHubDeliveryManagerRes;
import animal.dto.ManagerResponse.GetHubManagerRes;
import animal.dto.ManagerResponse.GetProviderCompanyManagerRes;
import java.util.List;
import java.util.UUID;

public class HubResponse {

    public record GetHubRes(
        UUID id,
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
        List<GetHubDeliveryManagerRes> hubDeliveryManagerList,
        List<GetCompanyDeliveryManagerRes> companyDeliveryManagerList,
        List<GetProviderCompanyManagerRes> providerCompanyManagerList
    ) {

    }

    public record UpdateHubRes(
        UUID id,
        Address address,
        Coordinate coordinate,
        List<GetHubManagerRes> hubManagerList,
        List<GetHubDeliveryManagerRes> hubDeliveryManagerList,
        List<GetCompanyDeliveryManagerRes> companyDeliveryManagerList,
        List<GetProviderCompanyManagerRes> providerCompanyManagerList
    ) {

    }
}
