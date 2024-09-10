package animal.dto;

import animal.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SampleRequest {

    public record SignUpUserReq(
            @NotBlank(message = "이름칸이 비어있습니다.")
            String userName,

            String nickName,

            @NotBlank(message = "이메일칸이 비어있습니다.")
            @Email(message = "잘못된 이메일 주소 입니다.")
            String email,

            @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야합니다.")
            String phone,

            @Pattern(regexp = "^[A-Za-z\\d!@#$%&*()]{8,15}$",
                    message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 사용 가능하며, 최소 8자 이상 15자 이하 여야합니다.")
            String password,

            Address address
    ) {
    }
}
