package animal.auth.application;

import animal.auth.config.HubFeignClientConfig;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserResponse.CompanyUserRes;
import animal.auth.dto.UserResponse.DeliveryUserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "hub-service",
    configuration = HubFeignClientConfig.class,
    fallbackFactory = HubFallbackFactory.class,
    path = "/hubs"
)

public interface HubClient {

  /**
   * 배달담당자 등록 요청
   */
  @PostMapping("/delivery-user")
  void createDeliveryUser(@RequestBody UserRequest.SignUpDeliveryReq dto);

  /**
   * 배달담당자 데이터 요청
   */
  @GetMapping("/delivery-user/{username}")
  DeliveryUserRes GetDeliveryUserInfo(@PathVariable String username);

  /**
   * 배달담당자 정보 수정 요청
   */
  @PatchMapping("/delivery-user/{username}")
  void ModifyDeliveryUser(@PathVariable String username,
      @RequestBody UserRequest.UpdateDeliveryUserReq dto);


  /**
   * 업체 등록 요청
   */
  @PostMapping("/company-user")
  void createCompanyUser(@RequestBody UserRequest.SignUpCompanyReq dto);

  /**
   * 업체 데이터 요청
   */
  @GetMapping("/company-user/{username}")
  CompanyUserRes GetCompanyUserInfo(@PathVariable String username);

  /**
   * 사용자 삭제 요청
   */
  @DeleteMapping("/delivery-user/{username}")
  void DeleteUser(@PathVariable String username);
}
