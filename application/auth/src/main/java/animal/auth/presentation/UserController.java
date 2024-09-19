package animal.auth.presentation;

import animal.auth.application.UserService;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserResponse.UserRes;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;
import response.CommonResponse.CommonEmptyRes;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

 /* public static final String USERNAME = "X-User-Name";
  public static final String ROLE = "X-User-Roles";*/

    private final UserService userService;


    /**
     * 사용자 목록 조회 auth : 허브 관리자 (소속된 허브 회원 리스트 조회)
     */
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/list")
    public CommonResponse<Page<UserRes>> getUserList(Principal principal,
        @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        var response = userService.getUserList(pageable, principal.getName());
        return CommonResponse.success(response);
    }

    /**
     * 사용자 상세 조회 auth : 사용자 본인, 마스터 관리자
     */
    @PreAuthorize("hasRole('MASTER') or @securityPermission.isResorceOwner(authentication,#username)")
    @GetMapping("/{username}")
    public CommonResponse<UserRes> getUser(@PathVariable String username) {
        var response = userService.getUserInfo(username);
        return CommonResponse.success(response);
    }


    /**
     * 사용자 정보 수정 auth : 마스터 관리자
     */
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{username}")
    public CommonResponse<CommonEmptyRes> modifyCompanyUser(@PathVariable String username,
        @Valid @RequestBody UserRequest.ModifyUserReq request) {
        userService.modifyUser(username, request);
        return CommonResponse.success();
    }

    /**
     * 사용자 탈퇴 auth : 마스터 관리자
     */
    @PreAuthorize("hasRole('MASTER')")
    @DeleteMapping("/{username}")
    public CommonResponse<CommonEmptyRes> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return CommonResponse.success();
    }
}



