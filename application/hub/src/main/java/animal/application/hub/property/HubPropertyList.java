package animal.application.hub.property;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hubs")
public record HubPropertyList(
    List<HubProperty> list
) {

}
