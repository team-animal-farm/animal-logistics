package animal.application.order.presentaion;

import animal.application.order.application.DeliveryService;
import animal.application.order.application.order.OrderService;
import animal.application.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;

    /**
     * 주문 접수
     */
    @PostMapping
    public void createOrder(@Valid @RequestBody OrderResponse.CreateOrderReq request) {
        orderService.createOrder(request);
    }

    /**
     * 각 허브 정보 얻기
     */
    @GetMapping
    public void getHubInfo() {
        deliveryService.getHubInfo();
    }
}
