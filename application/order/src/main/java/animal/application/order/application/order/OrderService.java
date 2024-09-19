package animal.application.order.application.order;

import animal.application.order.application.DeliveryService;
import animal.application.order.client.CompanyClient;
import animal.application.order.client.HubClient;
import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.order.Order;
import animal.application.order.domain.order.OrderList;
import animal.application.order.dto.OrderResponse;
import animal.application.order.dto.OrderResponse.GetHubIdReq;
import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.dto.OrderResponse.GetProductRes;
import animal.application.order.infrastructure.OrderRepository;
import animal.application.order.mapper.OrderListMapper;
import animal.application.order.mapper.OrderMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderListMapper orderListMapper;
    private final HubClient hubClient;
    private final CompanyClient companyClient;
    private final DeliveryService deliveryService;

    public void createOrder(OrderResponse.CreateOrderReq dto) {

        GetHubIdReq hubIdDto = orderMapper.toGetHubIdReq(dto);

        // 도착허브 출발허브
        GetHubIdRes hubIds = hubClient.getHubId(hubIdDto);
        //재고 감소
        List<GetProductRes> productList = hubClient.adjustInventories(hubIds.startHubId(), dto.products());
        //수령업체 주소
        Address address = companyClient.getAddress(dto.receiveCompanyId());
        //주문 생성
        // todo : dto하고 받은 정보로 order생성,header의 username
        Order order = orderMapper.toOrder(dto, "username", address);

        List<OrderList> orderList = productList.stream()
            .map(res -> {
                OrderList product = orderListMapper.toOrderList(res); // OrderList로 변환
                product.updateReceiveCompany(dto.providerCompanyId()); // product의 receiveCompany 사용
                return product;
            })
            .toList();

        //주문
        order.addOrderList(orderList);

        orderRepository.save(order);

        //배달
        deliveryService.createDelivery(hubIdDto, address);

    }

}
