package animal.application.hub.domain.inventory;

import animal.jpa.BaseEntity;
import animal.application.hub.domain.hub.Hub;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    name = "p_inventory",
    indexes = {
        @Index(name = "idx_hub_id", columnList = "hub_id")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory extends BaseEntity {

    @EmbeddedId
    private InventoryId id;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    @Builder
    private Inventory(InventoryId id, Quantity quantity, Money price, Hub hub) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.hub = hub;
    }

    /**
     * 재고의 절대적인 수정
     */
    public void update(Quantity quantity, Money price) {
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * 재고의 수량을 조정
     */
    public void adjust(int quantity) {
        this.quantity = this.quantity.adjust(quantity);
    }
}
