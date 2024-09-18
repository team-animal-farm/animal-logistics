package animal.application.order.application;

import animal.application.order.domain.order.Order;
import animal.application.order.dto.OrderResponse.HubInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HubDeliveryAssigner {

    public void assign(List<Order> waitingOrderList, List<HubInfo> hubInfoList) {
        // TODO: 허브별 배송 담당자 배정
    }
}
