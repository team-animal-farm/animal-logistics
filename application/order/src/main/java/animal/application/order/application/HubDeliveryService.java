package animal.application.order.application;

import animal.application.order.domain.order.Order;
import animal.application.order.dto.OrderResponse.HubInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubDeliveryService {

    private final HubReader hubReader;
    private final OrderReader orderReader;
    private final HubDeliveryAssigner hubDeliveryAssigner;

    /**
     * 매일 08:00에 허브별 배송 담당자 배정
     */
    @Scheduled(cron = "0 0 8 * * *") // 매일 08:00
    @Transactional
    public void assignHubDeliveryToHubs() {
        log.info("assignHubDeliveryToHubs");
        // 1. 배송 대기인 주문 조회
        List<Order> waitingOrderList = orderReader.getWaitingOrderList();

        // 2. 배송 담당자 및 허브 조회
        List<HubInfo> hubInfoList = hubReader.read();

        // 3. 허브별 배송 담당자 배정
        hubDeliveryAssigner.assign(waitingOrderList, hubInfoList);
    }
}
