package animal.dto;

import java.util.UUID;

public class ProductResponse {

    public record CreateProductRes(
        UUID id,
        UUID hubId,
        UUID companyId,
        Long price,
        String name
    ) {

    }

    public record GetProductRes(
        UUID id,
        UUID hubId,
        UUID companyId,
        Long price,
        String name
    ) {

    }
}
