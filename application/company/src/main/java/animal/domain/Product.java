package animal.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @EmbeddedId
    private ProductId id;

    private UUID hubId;
    private UUID companyId;
    private Long price;
    private String name;

    @Builder
    private Product(UUID hubId, UUID companyId, Long price, String name) {
        this.hubId = hubId;
        this.companyId = companyId;
        this.price = price;
        this.name = name;
    }

}
