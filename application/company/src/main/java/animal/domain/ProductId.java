package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductId implements Serializable { // JPA 식별자 타입은 Serializable 구현해야 함

    @Column(name = "product_id")
    private UUID id;

    public static ProductId of(UUID id) {
        ProductId productId = new ProductId();
        productId.id = id;
        return productId;
    }

    public static ProductId ofRandom() {
        ProductId productId = new ProductId();
        productId.id = UUID.randomUUID();
        return productId;
    }
}