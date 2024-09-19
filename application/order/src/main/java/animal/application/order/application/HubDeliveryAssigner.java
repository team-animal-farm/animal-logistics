package animal.application.order.application;

import animal.application.order.domain.delivery.Delivery;
import animal.application.order.domain.delivery.DeliveryStatus;
import animal.application.order.domain.delivery.HubDeliveryManager;
import animal.application.order.dto.OrderResponse.HubInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HubDeliveryAssigner {

    public void assign(List<Delivery> deliveryList, List<HubInfo> hubInfoList) {

        List<HubDeliveryManager> hubDeliveryManagerList = hubInfoList.get(0).hubDeliveryManagerList();
        int count = hubDeliveryManagerList.size();

        for (int i = 0; i < deliveryList.size(); i++) {
            Delivery delivery = deliveryList.get(i);
            // 배송 담당자 지정
            delivery.updateHubDeliveryManager(hubDeliveryManagerList.get(i % count).getUsername());
            // 도착 허브에 도착한 것으로 변경
            delivery.updateDeliveryStatus(DeliveryStatus.ARRIVED_AT_HUB);
        }
    }
}
