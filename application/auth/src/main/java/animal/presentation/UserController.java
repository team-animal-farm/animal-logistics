package animal.presentation;

import animal.application.UserService;
import animal.dto.UserRequest;
import animal.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  //todo : CommonResponse 반환 타입 수정
  private final UserService userService;

  /**
   * 로그인
   */
  //todo : 필터로 변경 예정
  @GetMapping("/signIn")
  public CommonResponse createAuthenticationToken(@RequestBody String email) {
    final String response = userService.createAccessToken(email);
    return CommonResponse.success(response);
  }

  /**
   * 배송담당자 회원가입
   */
  @PostMapping("/delivery/signUp")
  public CommonResponse createDeliveryUser(@Valid @RequestBody UserRequest.SignUpDeliveryReq request) {
    userService.createDeliveryUser(request);
    return CommonResponse.success();
  }

  /**
   * 업체 회원가입
   */
  @PostMapping("/company/signUp")
  public CommonResponse createCompanyUser(@Valid @RequestBody UserRequest.SignUpCompanyReq request) {
    userService.createCompanyUser(request);
    return CommonResponse.success();
  }

  /**
   * 사용자 상세 조회
   */
  @GetMapping("/{username}")
  public CommonResponse getUser(@PathVariable String username) {
    UserResponse.GetUserRes response = userService.getUserInfo(username);
    return CommonResponse.success(response);
  }

  //필터 파라미터 - delivery, company
  //회원 리스트 조회 - 해당 허브 소속의 관리자가 소속된 허브 회원리스트를 조회
  //todo : 게이트 웨이 생성 후 다시 보기

  /**
   * 사용자 목록 조회
   */
  @GetMapping
  public CommonResponse getUserList(
      @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
  ) {
    userService.getUserList(pageable);
    return CommonResponse.success();
  }

  /**
   * 배송담당자 정보 수정
   */
  //사용자의 권한은 수정이 안됨,username
  @PatchMapping("/delivery/{username}")
  public CommonResponse modifyDeliveryUser(@PathVariable String username,
      @Valid @RequestBody UserRequest.ModifyDeliveryUserReq request) {
    userService.modifyDeliveryUser(username, request);
    return CommonResponse.success();
  }

  /**
   * 업체 정보 수정
   */
  //사용자의 권한은 수정이 안됨,username
  @PatchMapping("/company/{username}")
  public CommonResponse modifyCompanyUser(@PathVariable String username,
      @Valid @RequestBody UserRequest.ModifyUserReq request) {
    userService.modifyCompanyUser(username, request);
    return CommonResponse.success();
  }

  /**
   * 사용자 탈퇴
   */
  @DeleteMapping("/{username}")
  public CommonResponse deleteUser(@PathVariable String username) {
    userService.deleteUser(username);
    return CommonResponse.success();
  }
}


