package animal.application.hub.dto;

import animal.application.hub.domain.hub.Address;
import animal.application.hub.domain.hub.Coordinate;
import jakarta.validation.constraints.NotNull;

public class HubRequest {

    public record CreateHubReq(

        @NotNull(message = "주소는 필수입니다.")
        Address address,

        @NotNull(message = "좌푯값은 필수입니다.")
        Coordinate coordinate
    ) {

    }

    /**
     * 허브 수정 요청에 사용되는 DTO 값을 변경하지 않더라도 기존 값을 그대로 요청해야 함
     */
    public record UpdateHubReq(

        @NotNull(message = "주소는 필수입니다.")
        Address address,

        @NotNull(message = "좌푯값은 필수입니다.")
        Coordinate coordinate
    ) {

    }
}
