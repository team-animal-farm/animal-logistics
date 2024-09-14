package animal.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyUser extends User {

  @Column(nullable = false)
  private CompanyType type;

}
