package animal.domain;

import animal.dto.UserRequest.ModifyUserReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.util.UUID;
import jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
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
  private UserRole role;

 /* @Builder
  private User(UUID hubId, String nickname, String email, String phone, String password, Address address, UserRole role) {
    this.hubId = hubId;
    this.nickname = nickname;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.address = address;
    this.role = role;
  }*/

  public void updateInfo(ModifyUserReq dto) {
    this.hubId = dto.getHubId();
    this.nickname = dto.getNickname();
    this.email = dto.getEmail();
    this.phone = dto.getPhone();
    this.address = dto.getAddress();

  }
}
