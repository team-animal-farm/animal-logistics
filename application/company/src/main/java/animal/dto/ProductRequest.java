package animal.dto;

import java.util.UUID;

public class ProductRequest {

    public record CreateProductReq(
        UUID companyId,
        UUID hubId,
        String name,
        Long price
    ) {

    }

    public record UpdateProductReq(
        UUID productId,
        String name,
        Long price
    ) {

    }
}
