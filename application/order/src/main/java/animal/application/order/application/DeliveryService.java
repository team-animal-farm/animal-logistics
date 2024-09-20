package animal.application.order.application;

import animal.application.order.client.CompanyClient;
import animal.application.order.client.HubClient;
import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.delivery.Delivery;
import animal.application.order.domain.delivery.DeliveryPath;
import animal.application.order.dto.OrderResponse.GetHubIdReq;
import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.dto.OrderResponse.GetNode;
import animal.application.order.dto.OrderResponse.HubNode;
import animal.application.order.infrastructure.order.DeliveryRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {


    private final HubClient hubClient;
    private final CompanyClient companyClient;
    private final DeliveryRepository deliveryRepository;
    private final Random random = new Random();

    List<GetNode> hubList;

    public void createDelivery(GetHubIdReq dto, Address address, GetHubIdRes hubIds) {

        // 수령인
        // String recipient = companyClient.getRecipient(dto.receiveCompanyId());

        List<GetNode> hubList = hubClient.getHubList();
        hubList.sort(Comparator.comparing(GetNode::seq));
        List<HubNode> path = new ArrayList<>();
        for (GetNode n : hubList) {
            if (n.HubId() == hubIds.startHubId() || n.HubId() == hubIds.endHubId()) {
                HubNode node = createHubNode(n.HubId(), n.seq());
                path.add(node);
            }
        }
        // 총 예상 시간과 거리 계산
        int totalEstimatedTime = path.stream()
            .mapToInt(HubNode::estimatedTime)
            .sum();

        double totalEstimatedDistance = path.stream()
            .mapToDouble(hubNode -> {
                String distanceStr = hubNode.estimatedDistance();
                return Double.parseDouble(distanceStr.replaceAll("[^0-9.]", ""));
            })
            .sum();
        //Map<String, Object> map = hubUtil.estimatedData(hubIds);
        // DeliveryPath 생성
        DeliveryPath deliveryPath = DeliveryPath.builder()
            .startHubId(hubIds.startHubId())
            .endHubId(hubIds.endHubId())
            .estimatedDistance(totalEstimatedDistance)
            .estimatedTime(totalEstimatedTime)
            .build();

        // Delivery 생성 및 저장
        Delivery delivery = Delivery.builder()
            .startHubId(dto.providerCompanyId())
            .endHubId(dto.receiveCompanyId())
            .address(address)
            .recipient("recipient")
            .deliveryPath(deliveryPath)
            .build();

        deliveryRepository.save(delivery);
    }


    private HubNode createHubNode(UUID startId, Integer sequence) {
        Integer newEstimatedTime = random.nextInt(121); // 0~120 분 랜덤
        Integer randomDistance = random.nextInt(1000) + 1; // 1~1000 미터 랜덤
        String newEstimatedDistance = randomDistance + "m";
        return new HubNode(startId, sequence, newEstimatedTime, newEstimatedDistance);
    }

    //허브 정보 초기화
    public void getHubInfo() {
        // 허브 모든 리스트 받아오기
        List<GetNode> hubList = hubClient.getHubList();
        hubList.sort(Comparator.comparing(GetNode::seq));

        //노드에 저장
        //hubUtil.init(hubList);

    }


}
