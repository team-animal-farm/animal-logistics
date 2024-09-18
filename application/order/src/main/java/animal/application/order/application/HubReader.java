package animal.application.order.application;

import animal.application.order.client.HubClient;
import animal.application.order.dto.OrderResponse.HubInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubReader {

    private final HubClient hubClient;

    /**
     * 허브 서비스로부터 허브 정보 조회
     */
    public List<HubInfo> read() {
        return hubClient.getHubs().data();
    }
}
