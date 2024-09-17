package response;

public enum DeliveryStatus {
    // 허브 배송 준비중 -> 허브 배송 중 -> 회사 배송 준비중 -> 회사 배송 중 -> 배송 완료
    HUB_READY, HUB_ON_DELIVERY, COMPANY_READY, COMPANY_ON_DELIVERY, DELIVERED
}
