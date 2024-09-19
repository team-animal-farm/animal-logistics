package animal.application.order.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  WAITING_DELIVERY("배송 대기"),
  DELIVERING("배송 중"),
  COMPLETED_DELIVERY("배송 완료"),
  CANCEL_ORDER("주문 취소");

  private final String status;
}
