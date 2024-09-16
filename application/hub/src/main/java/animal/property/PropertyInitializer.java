package animal.property;

import animal.domain.Hub;
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
    }
}
