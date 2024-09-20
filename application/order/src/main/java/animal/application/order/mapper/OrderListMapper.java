package animal.application.order.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.application.order.domain.order.OrderList;
import animal.application.order.dto.OrderResponse.OrderProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING) // 빈으로 주입받을 수 있음
public interface OrderListMapper {

    OrderList toOrderList(OrderProduct dto);

}
