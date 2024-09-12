package animal.dto;

import animal.domain.Address;
import animal.domain.Coordinate;
import animal.domain.manager.CompanyDeliveryManager;
import animal.domain.manager.HubDeliveryManager;
import animal.domain.manager.HubManager;
import animal.domain.manager.ProviderCompanyManager;
import java.util.List;
import java.util.UUID;

public class HubResponse {

    public record GetHubRes(
        UUID id,
        Address address,
        Coordinate coordinate,
        List<HubManager> hubManagerList,
        List<HubDeliveryManager> hubDeliveryManagerList,
        List<CompanyDeliveryManager> companyDeliveryManagerList,
        List<ProviderCompanyManager> providerCompanyManagerList
    ) {

    }

    public record CreateHubRes(
        UUID id,
        Address address,
        Coordinate coordinate,
        List<HubManager> hubManagerList,
        List<HubDeliveryManager> hubDeliveryManagerList,
        List<CompanyDeliveryManager> companyDeliveryManagerList,
        List<ProviderCompanyManager> providerCompanyManagerList
    ) {

    }

    public record UpdateHubRes(
        UUID id,
        Address address,
        Coordinate coordinate,
        List<HubManager> hubManagerList,
        List<HubDeliveryManager> hubDeliveryManagerList,
        List<CompanyDeliveryManager> companyDeliveryManagerList,
        List<ProviderCompanyManager> providerCompanyManagerList
    ) {

    }
}
