package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Compan extends User {

  @Column(nullable = false)
  private CompanyType type;

}
