package animal.dto;

import animal.domain.Address;
import animal.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SampleResponse {

    public record getUserResponse(
            String username,

            String nickname,

            String email,

            String phone,

            String password,

            Address address,

            UserRole role
    ) {
    }
}
