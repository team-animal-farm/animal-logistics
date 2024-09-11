package animal.domain;

import animal.jpa.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_hub")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseEntity {

    @EmbeddedId
    private final HubId id = HubId.ofRandom();

    @Embedded
    private Address address;

    @Embedded
    private Coordinate coordinate;

    @Builder
    private Hub(Address address, Coordinate coordinate) {
        this.address = address;
        this.coordinate = coordinate;
    }

    public void updateHubInfo(Address address, Coordinate coordinate) {
        this.address = address;
        this.coordinate = coordinate;
    }
}
