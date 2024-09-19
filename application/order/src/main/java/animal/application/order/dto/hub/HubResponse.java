package animal.application.order.dto.hub;

import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.delivery.Coordinate;
import java.util.List;
import java.util.UUID;

public class HubResponse {

    public record GetHubIdListRes(
        List<UUID> hubIds
    ) {

    }

    public record GetHubRes(
        UUID id,
        String name,
        Integer sequence,
        Address address,
        Coordinate coordinate,
        List<GetCompanyDeliveryManagerRes> companyDeliveryManagerList
    ) {

    }

    public record GetCompanyDeliveryManagerRes(
        String username,
        String slackId
    ) {

    }
}
