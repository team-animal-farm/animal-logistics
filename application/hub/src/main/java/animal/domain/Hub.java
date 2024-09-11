package animal.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jpa.BaseEntity;
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

    @OneToMany(mappedBy = "hub", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Inventory> inventoryList = new ArrayList<>();

    @Builder
    private Hub(Address address, Coordinate coordinate) {
        this.address = address;
        this.coordinate = coordinate;
    }

    /**
     * 허브 정보 수정
     */
    public void updateHubInfo(Address address, Coordinate coordinate) {
        this.address = address;
        this.coordinate = coordinate;
    }

    /**
     * 재고 추가
     */
    public void addInventory(Inventory inventory) {
        inventoryList.add(inventory);
    }

    /**
     * 재고 단건 조회
     */
    public Optional<Inventory> getInventory(InventoryId inventoryId) {

        return inventoryList.stream()
            .filter(inventory -> inventory.getId().equals(inventoryId))
            .findAny();
    }

    /**
     * 재고 삭제
     */
    public void removeInventory(InventoryId inventoryId) {
        inventoryList.removeIf(inventory -> inventory.getId().equals(inventoryId));
    }
}
