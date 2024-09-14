package animal.domain;

import animal.jpa.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "p_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @EmbeddedId
    private ProductId id;

    private UUID hubId;
    private Long price;
    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    private Product(ProductId productId, UUID hubId, Long price, String name, Company company) {
        this.id = productId;
        this.hubId = hubId;
        this.price = price;
        this.name = name;
        this.company = company;
    }

    public void update(String name, Long price) {
        this.name = name;
        this.price = price;
    }
}
