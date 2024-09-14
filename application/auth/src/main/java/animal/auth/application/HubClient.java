package animal.auth.application;

import animal.auth.dto.UserRequest;
import animal.auth.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import security.UserRole;

@FeignClient(
    name = "hub-service",
    fallbackFactory = HubFallbackFactory.class
)
public interface HubClient {

  /**
   * 배달담당자 등록 요청
   */

  @PostMapping("/hubs/deliveryUser/create")
  void createDeliveryUser(@RequestBody UserRequest.SignUpDeliveryReq dto);

  /**
   * 업체 등록 요청
   */
  @PostMapping("/hubs/companyUser/create")
  void createCompanyUser(@RequestBody UserRequest.SignUpCompanyReq dto);

  /**
   * 배달담당자 데이터 요청
   */
  //todo : 사용자 role과 pk를 전송하면 GetInfoUserReq로 매핑
  @GetMapping("/hubs/deliveryUser/{username}")
  UserResponse.CompanyUserRes GetCompanyUserInfo(UserRole role);

  /**
   * 업체 데이터 요청
   */
  //todo : 사용자 role과 pk를 전송하면 GetInfoUserReq로 매핑
  @GetMapping("/hubs/companyUser/{username}")
  UserResponse.DeliveryUserRes GetDeliveryUserInfo(UserRole role);

  /**
   * 슬랙 아이디 변경
   */
  @PostMapping("/hubs/modify/slackId/{usrename}")
  void ModifySlackId(String slackId);
}
