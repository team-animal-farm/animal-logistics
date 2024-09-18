package animal.application.order.domain.delivery;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    WAITING_AT_HUB("허브 대기중"),
    MOVING_TO_HUB("허브 이동중"),
    ARRIVED_AT_HUB("목적지 허브 도착"),
    DELIVERING("배송중"),
    DELIVERED("배송 완료");


    private final String status;
}
