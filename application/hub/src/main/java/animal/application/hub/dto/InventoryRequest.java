package animal.application.hub.dto;

import animal.application.hub.domain.inventory.Money;
import animal.application.hub.domain.inventory.Quantity;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class InventoryRequest {

    public record CreateInventoryReq(

        @NotNull(message = "상품 id는 필수입니다.")
        UUID productId,

        @NotNull(message = "수량은 필수입니다.")
        Quantity quantity,

        @NotNull(message = "가격은 필수입니다.")
        Money price
    ) {

    }

    public record UpdateInventoryReq(

        @NotNull(message = "수량은 필수입니다.")
        Quantity quantity,

        @NotNull(message = "가격은 필수입니다.")
        Money price
    ) {

    }

    public record AdjustInventoryReq(

        @NotNull(message = "상품 id는 필수입니다.")
        UUID productId,

        @NotNull(message = "수량은 필수입니다.")
        Integer quantity
    ) {

    }
}
