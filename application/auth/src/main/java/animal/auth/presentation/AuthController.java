package animal.auth.presentation;


import animal.auth.application.UserService;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserRequest.SignInUserReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;
import response.CommonResponse.CommonEmptyRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

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

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public CommonResponse<String> login(@RequestBody SignInUserReq request) {
        var token = userService.loginUser(request);
        return CommonResponse.success(token);

    }
}
