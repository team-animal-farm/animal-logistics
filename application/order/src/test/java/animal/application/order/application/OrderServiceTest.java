package animal.application.order.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import animal.application.order.application.order.CompanyClient;
import animal.application.order.application.order.HubClient;
import animal.application.order.application.order.OrderService;
import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.order.Order;
import animal.application.order.domain.order.OrderList;
import animal.application.order.dto.OrderResponse;
import animal.application.order.dto.OrderResponse.GetProductRes;
import animal.application.order.infrastructure.OrderRepository;
import animal.application.order.mapper.OrderListMapper;
import animal.application.order.mapper.OrderMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderMapper orderMapper;

  @Mock
  private OrderListMapper orderListMapper;

  @Mock
  private HubClient hubClient;

  @Mock
  private CompanyClient companyClient;

  @InjectMocks
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createOrder_shouldCreateAndSaveOrderSuccessfully() {
    // Given
    UUID providerCompanyId = UUID.randomUUID();
    UUID receiveCompanyId = UUID.randomUUID();
    OrderResponse.CreateOrderReq createOrderReq = mock(OrderResponse.CreateOrderReq.class);
    when(createOrderReq.providerCompanyId()).thenReturn(providerCompanyId);
    when(createOrderReq.receiveCompanyId()).thenReturn(receiveCompanyId);

    // Mock GetProductRes list from hubClient
    GetProductRes productRes = mock(GetProductRes.class);
    List<GetProductRes> productList = List.of(productRes);
    when(hubClient.adjustInventories(providerCompanyId, createOrderReq.products())).thenReturn(productList);

    // Mock Address from companyClient
    Address address = mock(Address.class);
    when(companyClient.getAddress(receiveCompanyId)).thenReturn(address);

    // Mock Order and OrderList mapping
    Order order = mock(Order.class); // Ensure this is not null
    when(orderMapper.toOrder(any(), anyString(), any())).thenReturn(order);

    OrderList orderList = mock(OrderList.class);
    when(orderListMapper.toOrderList(productRes)).thenReturn(orderList);

    // When
    orderService.createOrder(createOrderReq);

    // Then
    verify(orderRepository, times(1)).save(order);
    verify(order, times(1)).addOrderList(anyList());
    verify(orderList, times(1)).updateReceiveCompany(providerCompanyId);
  }
}
