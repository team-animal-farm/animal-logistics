package animal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_hub_delivery_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubDeliveryManager {

    @Id
    private String username;

    private String slackId;

    @Builder
    private HubDeliveryManager(String username, String slackId) {
        this.username = username;
        this.slackId = slackId;
    }
}
