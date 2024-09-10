package animal.presentation;

import animal.dto.SampleRequest;
import animal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import response.CommonResponse;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    // 로그인
    //todo : 필터로 변경 예정
    @GetMapping("/signIn")
    public CommonResponse createAuthenticationToken(final Principal principal) {
        final String response = userService.createAccessToken(principal.getName());
        return CommonResponse.success();
    }

    // 회원가입
    @PostMapping("/signUp")
    public CommonResponse createUser(@RequestBody SampleRequest.SignUpUserReq request) {
        userService.createUser(request);
        return CommonResponse.success();
    }

    //회원 상세 조회
    @GetMapping("/{userId}")
    public CommonResponse getUser(@PathVariable(name = "userId") String username) {
        return CommonResponse.success();
    }

    //회원 리스트 조회
    @GetMapping("")
    public CommonResponse getUserList() {
        return CommonResponse.success();
    }

    //회원 정보 수정
    @PutMapping("/{userId}")
    public CommonResponse modifyUser(@PathVariable(name = "userId") String username) {
        return CommonResponse.success();
    }

    //회원 탈퇴
    @DeleteMapping("/{userId}")
    public CommonResponse deleteUser(@PathVariable String userName) {
        return CommonResponse.success();
    }
}



