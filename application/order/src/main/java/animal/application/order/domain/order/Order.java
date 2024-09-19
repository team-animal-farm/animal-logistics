package animal.application.order.domain.order;

import animal.application.order.domain.delivery.Address;
import animal.application.order.domain.delivery.Delivery;
import animal.application.order.dto.OrderResponse;
import animal.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order")
@SQLRestriction("deleted_at IS NULL")
public class Order extends BaseEntity {

  @Id
  private final UUID id = UUID.randomUUID();

  @Column(nullable = false)
  private UUID providerId;

  @Column(nullable = false)
  private String username;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "order_id")
  private Delivery delivery;

  @OneToMany(mappedBy = "order",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<OrderList> orderList = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private String comment;

  private Address address;

  @Builder
  private Order(OrderResponse.CreateOrderReq dto, String username, Address address) {
    this.providerId = dto.providerCompanyId();
    this.comment = dto.comment();
    this.status = OrderStatus.WAITING_DELIVERY;
    this.username = username;
    this.address = address;
  }

  public void addOrderList(List<OrderList> orderList) {
    this.orderList.addAll(orderList);
  }
}
