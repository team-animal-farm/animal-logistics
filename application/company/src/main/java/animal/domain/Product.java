package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(name = "product_id")
    private UUID id;
    private UUID hubId;
    private UUID companyId;
    private Long price;
    private String name;

    @Builder
    public Product(UUID hubId, UUID companyId, Long price, String name) {
        this.hubId = hubId;
        this.companyId = companyId;
        this.price = price;
        this.name = name;
    }

}
