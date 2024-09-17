package animal.application.order.application;

import animal.application.order.domain.Delivery;
import animal.application.order.dto.hub.HubResponse.GetHubRes;
import animal.application.order.dto.user.UserResponse.GetDeliveryDriver;
import animal.application.order.infrastructure.hub.HubClient;
import animal.application.order.infrastructure.order.DeliveryRepository;
import animal.application.order.infrastructure.user.UserClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import response.DeliveryStatus;
import response.UserRole;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final HubClient hubClient;
    private final UserClient userClient;

    @Scheduled(cron = "0 0 6 * * *") // 매일 06:00
    public void assignCompanyDeliveryToReceiver() {
        // 허브 ID 조회
        GetHubRes hubIdList = hubClient.getHubId();
        // 허브 ID로 조회하는데, 배송 대기 중인 건
        hubIdList.hubIds().forEach(hubId -> {
            // 배송 대기 건 조회
            List<Delivery> deliveryList = deliveryRepository.findByHubIdAndDeliveryStatus(hubId, DeliveryStatus.COMPANY_READY);

            int totalDelivery = deliveryList.size();
            // deliveryList 가 null이면 continue
            if (deliveryList.isEmpty()) {
                return;
            }

            // 배송기사 조회
            List<GetDeliveryDriver> deliveryDriver = userClient.getDeliveryDriver(hubId, UserRole.DELIVERY_COMPANY,
                deliveryList.size());

            // 배송기사에게 지정된 배송 리스트
            Map<String, Integer> deliveryDriverMap = new HashMap<>();

            for (int i = 0; i < deliveryDriver.size(); i++) {
                for (int j = 0; j <= deliveryList.size() / 10; j++) {
                    int delivery = i + j * 10;
                    if (totalDelivery < delivery) {
                        break;
                    }
                    // 배달 건에 배달기사 배정
                    deliveryDriverMap.put(deliveryDriver.get(i).username(), j);

                    deliveryList.get(delivery).updateDriver(deliveryDriver.get(i).username());
                }
            }

            deliveryDriverMap.forEach((key, value) -> {
                // 배송기사에게 배송 리스트 전송
                
            });
        });

        // 4. 거리, 예상 시간 계산 후 업데이트
        // 5. Slack 알림 전송
    }


}
