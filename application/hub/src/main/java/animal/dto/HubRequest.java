package animal.dto;

import animal.domain.Address;
import animal.domain.Coordinate;
import jakarta.validation.constraints.NotNull;

public class HubRequest {

    public record CreateHubReq(

        @NotNull(message = "주소는 필수입니다.")
        Address address,
        
        @NotNull(message = "좌푯값은 필수입니다.")
        Coordinate coordinate
    ) {

    }
}
