package animal.application.order.mapper;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.order.Order;
import animal.application.order.dto.OrderResponse;
import animal.application.order.dto.OrderResponse.CreateOrderReq;
import animal.application.order.dto.OrderResponse.GetHubIdReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface OrderMapper {

  Order toOrder(OrderResponse.CreateOrderReq dto, String username, Address address);

  GetHubIdReq toGetHubIdReq(CreateOrderReq dto);

}
