package animal.property;

import animal.domain.Hub;
import animal.domain.HubDeliveryManager;
import animal.infrastructure.HubDeliveryManagerRepository;
import animal.infrastructure.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PropertyInitializer implements InitializingBean {

    private static int sequence = 1;

    private final HubPropertyList hubPropertyList;
    private final HubRepository hubRepository;
    private final HubDeliveryManagerRepository hubDeliveryManagerRepository;

    @Override
    @Transactional
    public void afterPropertiesSet() {
        for (HubProperty hubProperty : hubPropertyList.list()) {

            Hub hub = Hub.builder()
                .name(hubProperty.name())
                .address(hubProperty.address())
                .coordinate(hubProperty.coordinate())
                .sequence(sequence++)
                .build();

            hubRepository.save(hub);
        }

        for (int i = 0; i < 10; i++) {
            HubDeliveryManager manager = HubDeliveryManager.builder()
                .username("hub-" + i)
                .slackId("slack-" + i)
                .build();
            hubDeliveryManagerRepository.save(manager);
        }
    }
}
