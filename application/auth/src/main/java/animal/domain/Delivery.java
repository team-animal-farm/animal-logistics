package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends User {

  @Column(nullable = false)
  private DeliveryType type;

  @Column(nullable = false)
  private String slackId;

}
