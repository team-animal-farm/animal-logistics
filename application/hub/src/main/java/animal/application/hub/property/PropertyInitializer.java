package animal.application.hub.property;

import animal.application.hub.domain.hub.Hub;
import animal.application.hub.domain.manager.HubDeliveryManager;
import animal.application.hub.domain.manager.CompanyDeliveryManager;
import animal.application.hub.domain.manager.SlackId;
import animal.application.hub.domain.manager.Username;
import animal.application.hub.infrastructure.HubDeliveryManagerRepository;
import animal.application.hub.infrastructure.HubRepository;
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

            Hub hub = hubRepository.save(Hub.builder()
                .name(hubProperty.name())
                .address(hubProperty.address())
                .coordinate(hubProperty.coordinate())
                .sequence(sequence++)
                .build());

            hub.addCompanyDeliveryManager(
                CompanyDeliveryManager.builder()
                    .username(Username.of("cdm" + sequence))
                    .slackId(SlackId.of("cdm" + sequence))
                    .build());

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
