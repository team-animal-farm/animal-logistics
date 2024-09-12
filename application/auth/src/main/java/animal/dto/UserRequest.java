package animal.dto;

import animal.domain.Address;
import animal.domain.CompanyType;
import animal.domain.DeliveryType;
import animal.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRequest {

  @Getter
  public abstract static class SignUpUserReq {

    @NotBlank(message = "사용자 이름은 필수 입력입니다.")
    private String username;

    private String nickname;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "잘못된 이메일 주소 입니다.")
    private String email;

    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
        message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야합니다.")
    private String phone;

    @Pattern(regexp = "^[A-Za-z\\d!@#$%&*()]{8,15}$",
        message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 사용 가능하며, 최소 8자 이상 15자 이하 여야합니다.")
    private String password;

    private UUID hubId;

    private UserRole role;

    private Address address;
  }

  @Getter
  @AllArgsConstructor
  public static class SignUpDeliveryReq extends SignUpUserReq {

    private DeliveryType type;

    @NotBlank(message = "슬랙 id는 필수 입력입니다.")
    private String slackId;
  }

  @Getter
  @AllArgsConstructor
  public static class SignUpCompanyReq extends SignUpUserReq {

    private CompanyType type;
  }

  @Getter
  public abstract static class ModifyUserReq {

    private String nickname;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "잘못된 이메일 주소 입니다.")
    private String email;

    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
        message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야합니다.")
    private String phone;

    private UUID hubId;

    private Address address;
  }

  @Getter
  @AllArgsConstructor
  public static class ModifyDeliveryUserReq extends ModifyUserReq {

    //만약
    private DeliveryType type;
    @NotBlank(message = "슬랙 id는 필수 입력입니다.")
    private String slackId;
  }

  @Getter
  @AllArgsConstructor
  public static class ModifyCompanyUserReq extends ModifyUserReq {

    //만약 하면 공급-> 수량 type변경 시 company의 상품 모두 삭제
    private CompanyType type;
  }


}
