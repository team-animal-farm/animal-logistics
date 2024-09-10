package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends User {

  @Column(nullable = false)
  private DeliveryType type;

  @Column(nullable = false)
  private String slackId;


}
