package animal.application.order.dto;

import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.delivery.Coordinate;
import animal.application.order.domain.delivery.HubDeliveryManager;
import java.util.List;
import java.util.UUID;

public class OrderResponse {

    public record HubData(
        List<HubInfo> data
    ) {

    }

    public record HubInfo(
        UUID id,
        String name,
        Integer sequence,
        Address address,
        Coordinate coordinate,
        List<HubDeliveryManager> hubDeliveryManagerList
    ) {

    }

    public record CreateOrderReq(
        UUID receiveCompanyId,
        UUID providerCompanyId,
        List<OrderProduct> products,
        String comment
    ) {

    }

    public record OrderProduct(
        UUID productId,
        Integer quantity
    ) {

    }

    public record GetProductRes(
        UUID productId,
        Integer quantity,
        Integer price
    ) {

    }

    public record GetHubIdReq(
        UUID receiveCompanyId,
        UUID providerCompanyId
    ) {

    }

    public record GetHubIdRes(
        UUID endHubId,
        UUID startHubId
    ) {

    }


}
