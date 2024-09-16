package animal.application.order.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HubDeliveryService {

    /**
     * 매일 08:00에 허브별 배송 담당자 배정
     */
    @Scheduled(cron = "0 0 8 * * *") // 매일 08:00
    public void assignHubDeliveryToHubs() {
        // 1. 배송 대기인 주문 조회
        // 2. 배송 담당자 조회
        // 3. 허브 조회
        // 4. 허브별 배송 담당자 배정
    }
}
