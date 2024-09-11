package animal.dto;

import animal.domain.Address;
import animal.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class UserResponse {

    public record GetUserRes(
            String username,

            String nickname,

            String email,

            String phone,

            Address address,

            UUID hubName,

            UserRole role
    ) {
    }
}
