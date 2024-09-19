package animal.application.order.application;

import animal.application.order.application.order.CompanyClient;
import animal.application.order.application.order.HubClient;
import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.delivery.Delivery;
import animal.application.order.domain.delivery.DeliveryPath;
import animal.application.order.dto.OrderResponse.GetHubIdReq;
import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.infrastructure.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final HubClient hubClient;
  private final CompanyClient companyClient;
  private final DeliveryRepository deliveryRepository;

  public void CreateDelivery(GetHubIdReq dto, Address address) {
    //도착허브 출발허브
    GetHubIdRes hubIds = hubClient.getHubId(dto);
    //수령인
    String recipient = companyClient.getRecipient(dto.receiveCompanyId());

    //todo : mapper로 관리

    //todo : map api와 재귀적 탐색으로 최적화된 예상 시간, 거리를 넣은 값으로 DeliveryPath생성하기
    DeliveryPath deliveryPath = DeliveryPath.builder()
        .startHubId(hubIds.startHubId())
        .endHubId(hubIds.endHubId())
        .build();

    Delivery delivery = Delivery.builder()
        .startHubId(dto.providerCompanyId())
        .endHubId(dto.receiveCompanyId())
        .address(address)
        .recipient(recipient)
        .deliveryPath(deliveryPath)
        .build();

    deliveryRepository.save(delivery);
  }

}
