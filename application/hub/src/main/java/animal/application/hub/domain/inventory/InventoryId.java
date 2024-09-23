package animal.application.hub.domain.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 재고의 id 값객체
 */
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryId implements Serializable { // JPA 식별자 타입은 Serializable 구현해야 함

    @Column(name = "id")
    private UUID id;

    public static InventoryId of(UUID id) {
        InventoryId inventoryId = new InventoryId();
        inventoryId.id = id;
        return inventoryId;
    }

    public static InventoryId ofRandom() {
        InventoryId inventoryId = new InventoryId();
        inventoryId.id = UUID.randomUUID();
        return inventoryId;
    }
}
