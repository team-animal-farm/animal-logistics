package animal.presentation;

import animal.application.ProductService;
import animal.domain.CompanyId;
import animal.domain.ProductId;
import animal.dto.ProductRequest;
import animal.dto.ProductResponse.CreateProductRes;
import animal.dto.ProductResponse.GetProductRes;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;
import response.CommonResponse.CommonEmptyRes;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록 요청 API 업체가 상품을 등록 후 hub에 등록 요청
     */
    @PostMapping
    public CommonResponse<CreateProductRes> createProduct(@RequestBody ProductRequest.CreateProductReq createProductReq) {
        var createProductRes = productService.createProduct(createProductReq);
        return CommonResponse.success(createProductRes);
    }

    /**
     * 상품 수정 API
     */
    @PatchMapping("/{companyId}")
    public CommonResponse<CommonEmptyRes> updateProduct(
        @PathVariable UUID companyId,
        @RequestBody ProductRequest.UpdateProductReq updateProductReq) {
        productService.updateProduct(CompanyId.of(companyId), updateProductReq);
        return CommonResponse.success();
    }

    /**
     * 상품 삭제 API
     */
    @DeleteMapping("/{companyId}/{productId}")
    public CommonResponse<CommonEmptyRes> deleteProduct(
        @PathVariable UUID companyId,
        @PathVariable UUID productId
    ) {
        productService.deleteProduct(CompanyId.of(companyId), ProductId.of(productId));
        return CommonResponse.success();
    }

    /**
     * 상품 상세 조회 API
     */
    @GetMapping("/{companyId}/{productId}")
    public CommonResponse<GetProductRes> getProduct(
        @PathVariable UUID companyId,
        @PathVariable UUID productId
    ) {
        var getProductRes = productService.getProduct(CompanyId.of(companyId), ProductId.of(productId));
        return CommonResponse.success(getProductRes);
    }

    /**
     * 상품 검색 API
     */
    @GetMapping("/{companyId}")
    public CommonResponse<Page<GetProductRes>> searchProduct(
        @PathVariable UUID companyId,
        @RequestParam String productName,
        Pageable pageable
    ) {
        var getProductRes = productService.getProductList(CompanyId.of(companyId), productName, pageable);
        return CommonResponse.success(getProductRes);
    }


}
