package animal.presentation;

import animal.application.ProductService;
import animal.dto.ProductRequest;
import animal.dto.ProductResponse.CreateProductRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;

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


}
