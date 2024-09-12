package animal.application;

import animal.dto.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "hub-service",
    fallbackFactory = HubFallbackFactory.class
)
public interface HubClient {

  /**
   * delivery user 등록 요청
   */
  @PostMapping("/hubs /deliveryUser/create")
  String createDeliveryUser(@RequestBody UserRequest.SignUpDeliveryReq dto);

  /**
   * company user 등록 요청
   */
  @PostMapping("/hubs/company/create")
  String createCompanyUser(@RequestBody UserRequest.SignUpCompanyReq dto);

  /**
   * 사용자 데이터 요청
   */
  //todo : 사용자 role과 pk를 전송하면 GetInfoUserReq로 매핑
  /*@GetMapping("/hubs")
  String GetUserInfo(UserRole role, String username);*/
}
