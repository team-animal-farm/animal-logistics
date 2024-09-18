package animal.application.order.domain.order;

import animal.application.order.dto.OrderResponse.GetProductRes;
import animal.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_orderList")
@SQLRestriction("deleted_at IS NULL")
public class OrderList extends BaseEntity {

  @Id
  private final UUID id = UUID.randomUUID();

  @Column(nullable = false)
  private UUID productId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "order_id")
  private Order order;

  @Column(nullable = false)
  private UUID receiveCompany;

  private Integer quantity;

  private Integer price;

  @Builder
  public OrderList(GetProductRes dto) {
    this.productId = dto.productId();
    this.quantity = dto.quantity();
    this.price = dto.price();

  }

  public void updateReceiveCompany(UUID receiveCompany) {
    this.receiveCompany = receiveCompany;
  }
}
