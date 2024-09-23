package animal.application.hub.dto;

import animal.application.hub.domain.inventory.Money;
import animal.application.hub.domain.inventory.Quantity;
import java.util.UUID;

public class InventoryResponse {

    public record CreateInventoryRes(
        UUID id,
        Quantity quantity,
        Money price
    ) {

    }

    public record GetInventoryRes(
        UUID id,
        Quantity quantity,
        Money price
    ) {

    }

    public record UpdateInventoryRes(
        UUID id,
        Quantity quantity,
        Money price
    ) {

    }

    public record AdjustInventoryRes(
        UUID id,
        Quantity quantity,
        Money price
    ) {

    }
}
