package animal.auth.presentation;

import animal.auth.application.UserService;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserResponse.UserRes;
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
import response.CommonResponse.CommonEmptyRes;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 배송담당자 회원가입
     */
    @PostMapping("/delivery/sign-up")
    public CommonResponse<CommonEmptyRes> createDeliveryUser(@Valid @RequestBody UserRequest.SignUpDeliveryReq request) {
        userService.createDeliveryUser(request);
        return CommonResponse.success();
    }

    /**
     * 업체 회원가입
     */
    @PostMapping("/company/sign-up")
    public CommonResponse<CommonEmptyRes> createCompanyUser(@Valid @RequestBody UserRequest.SignUpCompanyReq request) {
        userService.createCompanyUser(request);
        return CommonResponse.success();
    }

    //필터 파라미터 - delivery, company
    //회원 리스트 조회 - 해당 허브 소속의 관리자가 소속된 허브 회원리스트를 조회
    //todo : 게이트 웨이 생성 후 다시 보기

    /**
     * 사용자 목록 조회
     */
    @GetMapping
    public CommonResponse<CommonEmptyRes> getUserList(
        @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        userService.getUserList(pageable);
        return CommonResponse.success();
    }

    /**
     * 사용자 상세 조회
     */
    @GetMapping("/{username}")
    public CommonResponse<UserRes> getUser(@PathVariable String username) {
        var response = userService.getUserInfo(username);
        return CommonResponse.success(response);
    }


    /**
     * 사용자 정보 수정
     */
    @PatchMapping("/{username}")
    public CommonResponse<CommonEmptyRes> modifyCompanyUser(@PathVariable String username,
        @Valid @RequestBody UserRequest.ModifyUserReq request) {
        userService.modifyUser(username, request);
        return CommonResponse.success();
    }

    /**
     * 사용자 탈퇴
     */
    @DeleteMapping("/{username}")
    public CommonResponse<CommonEmptyRes> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return CommonResponse.success();
    }

}



