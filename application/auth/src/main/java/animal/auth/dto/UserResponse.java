package animal.auth.dto;

import animal.auth.domain.Address;
import animal.auth.domain.DeliveryType;
import java.util.UUID;
import response.CompanyType;
import security.UserRole;

public class UserResponse {

  //todo : 상속으로 변경
  public record GetUserRes(
      String username,

      String nickname,

      String email,

      String phone,

      Address address,

      UUID hubName,

      UserRole role
  ) {

  }

  public record DeliveryUserRes(
      DeliveryType type,
      String slackId
  ) {

  }

  public record CompanyUserRes(CompanyType type) {

  }
}
