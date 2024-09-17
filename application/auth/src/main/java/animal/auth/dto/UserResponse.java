package animal.auth.dto;

import animal.auth.domain.Address;
import animal.auth.domain.CompanyType;
import animal.auth.domain.DeliveryType;
import animal.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import security.UserRole;

public class UserResponse {

  @Getter
  @Setter
  @ToString(callSuper = true)
  public abstract static class UserRes {

    protected String username;

    protected String nickname;

    protected String email;

    protected String phone;

    protected Address address;

    protected UserRole role;

    //허브 이름을 반환
    protected String hubName;

    public void updateUserRes(User user) {
      this.username = user.getUsername();
      this.nickname = user.getNickname();
      this.email = user.getEmail();
      this.phone = user.getPhone();
      this.address = user.getAddress();
      this.role = user.getRole();
    }

  }

  @Getter
  @AllArgsConstructor
  @ToString(callSuper = true)
  public static class DeliveryUserRes extends UserRes {

    private DeliveryType type;
    private String slackId;
  }

  @Getter
  @AllArgsConstructor
  @ToString(callSuper = true)
  public static class CompanyUserRes extends UserRes {

    private CompanyType type;
  }

}
