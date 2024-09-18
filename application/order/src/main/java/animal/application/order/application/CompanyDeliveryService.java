package animal.application.order.application;

import animal.application.order.domain.Delivery;
import animal.application.order.dto.hub.HubResponse.GetHubRes;
import animal.application.order.dto.user.UserResponse.GetDeliveryDriver;
import animal.application.order.infrastructure.hub.HubClient;
import animal.application.order.infrastructure.order.DeliveryRepository;
import animal.application.order.infrastructure.user.UserClient;
import exception.GlobalException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.DeliveryStatus;
import response.ErrorCase;
import response.UserRole;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final GeminiService geminiService;
    private final WeatherService weatherService;
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
            "Slack mrkdwn 서식을 적용해서 포맷을 예쁘게 꾸미고 " +
            "Slack mrkdwn 서식 적용 과정에서 Bold 처리는 반드시 *와 여백 한개로 감싸야 해 " +
            "(예시)오늘은 *흐리고 비* 가 내릴 예정)" +
            "그리고 이모티콘도 넣고 줄바꿈도 잘 적용해서 " +
            "한글로 500자 이내로 작성해줘", today);

//        try {
//            SlackIncomingHookDto slackRequest = new SlackIncomingHookDto("오늘의-날씨", result);
//            log.info(slackRestTemplate.postForObject(slackURL, slackRequest, String.class));
//            slackRepository.save(Slack.builder().receiverId("오늘의-날씨").message(result).sendTime(LocalDateTime.now()).build());
//        } catch (Exception e) {
//            log.error("Internal Server Error");
//            throw new CoreApiException(ErrorType.DEFAULT_ERROR);
//        }

        return result;
    }
}
