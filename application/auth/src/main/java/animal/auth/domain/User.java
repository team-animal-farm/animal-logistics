package animal.auth.domain;

import animal.auth.dto.UserRequest.ModifyUserReq;
import animal.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import security.UserRole;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "p_user")
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

  @Id
  private String username;

  @Column(nullable = false)
  private UUID hubId;

  private String nickname;

  @Column(unique = true)
  private String email;

  private String phone;

  private String password;

  private Address address;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role;

  public void updateInfo(ModifyUserReq dto) {
    this.hubId = dto.getHubId();
    this.nickname = dto.getNickname();
    this.email = dto.getEmail();
    this.phone = dto.getPhone();
    this.address = dto.getAddress();
  }
}
