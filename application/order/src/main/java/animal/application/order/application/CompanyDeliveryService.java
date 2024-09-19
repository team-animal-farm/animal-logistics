package animal.application.order.application;

import animal.application.order.client.HubClient;
import animal.application.order.domain.delivery.Delivery;
import animal.application.order.domain.delivery.DeliveryStatus;
import animal.application.order.dto.hub.HubResponse.GetCompanyDeliveryManagerRes;
import animal.application.order.dto.hub.HubResponse.GetHubIdListRes;
import animal.application.order.dto.hub.HubResponse.GetHubRes;
import animal.application.order.dto.slack.SlackMessageReq;
import animal.application.order.infrastructure.order.DeliveryRepository;
import animal.application.order.infrastructure.user.UserClient;
import exception.GlobalException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.CommonResponse;
import response.ErrorCase;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final GeminiService geminiService;
    private final SlackService slackService;
    private final WeatherService weatherService;
    private final HubClient hubClient;
    private final UserClient userClient;

    @Scheduled(cron = "0 0 6 * * *") // 매일 06:00
    @Transactional
    public void assignCompanyDeliveryToReceiver() {
        log.info("배송기사에게 배송 리스트 할당");
        // 허브 ID 조회
        GetHubIdListRes hubIdList = hubClient.getHubIdList();
        // 허브 ID로 조회하는데, 배송 대기 중인 건
        hubIdList.hubIds().forEach(hubId -> {
            // 배송 대기 건 조회
            List<Delivery> deliveryList = deliveryRepository.findByEndHubIdAndStatus(hubId, DeliveryStatus.ARRIVED_AT_HUB);

            int totalDelivery = deliveryList.size();
            // deliveryList 가 null이면 continue
            if (deliveryList.isEmpty()) {
                return;
            }
            // 배송기사 조회
            CommonResponse<GetHubRes> hub = hubClient.getHub(hubId);
            List<GetCompanyDeliveryManagerRes> deliveryDriver = hub.getData().companyDeliveryManagerList();

            // 배송기사에게 지정된 배송 리스트
            Map<Integer, List<Integer>> deliveryDriverMap = new HashMap<>();

            for (int i = 0; i < deliveryDriver.size(); i++) {
                for (int j = 0; j <= deliveryList.size() / 10; j++) {
                    int delivery = i + j * 10;
                    if (totalDelivery < delivery) {
                        break;
                    }
                    // 배달 건에 배달기사 배정
                    deliveryDriverMap.putIfAbsent(i, new ArrayList<>());

                    // 해당 key의 리스트에 값 추가
                    deliveryDriverMap.get(i).add(j);

                    // 담당 배송기사 update
                    deliveryList.get(delivery).updateDriver(deliveryDriver.get(i).username());
                }
            }
            deliveryDriverMap.forEach((key, value) -> {
                // 배송기사에게 배송 리스트 전송
                String slackId = deliveryDriver.get(key).slackId();
                List<Integer> deliveryListIndex = deliveryDriverMap.get(0);
                StringBuilder message = new StringBuilder();
                String weather = sendWeatherInfo();
                message.append(weather).append("\n\n");
                message.append("오늘 배송할 리스트는 다음과 같습니다.\n");
                int num = 1;
                for (int index : deliveryListIndex) {
                    // 배송지: 주소
                    message.append(num).append(". 배송지: ").append(deliveryList.get(index).getAddress()).append("\n");
                    num++;
                }

                // Slack 전송
                slackService.sendSlackMessage(SlackMessageReq.builder()
                    .message(message.toString())
                    .slackId(slackId)
                    .build());
                // TODO: 4. 거리, 예상 시간 계산 후 업데이트
            });
        });
    }

    @Transactional
    public String sendWeatherInfo() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = LocalDate.now().format(formatter);

        String requestText = "";
        try {
            requestText = weatherService.getWeatherInfo(today);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Internal Server Error");
            throw new GlobalException(ErrorCase.SYSTEM_ERROR);
        }

        // 날씨 정보 요약 요청
        String result = geminiService.getGeminiSummary(requestText + " 의 정보를 요약해서 오늘의 날씨를 알려주고 " +
            "그리고 이모티콘도 넣고 줄바꿈도 잘 적용해서 " +
            "한글로 500자 이내로 작성해줘", today);

        return result;
    }
}
