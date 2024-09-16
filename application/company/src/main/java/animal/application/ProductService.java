package animal.application;

import animal.domain.Company;
import animal.domain.CompanyId;
import animal.domain.Product;
import animal.domain.ProductId;
import animal.dto.ProductRequest.CreateProductReq;
import animal.dto.ProductRequest.UpdateProductReq;
import animal.dto.ProductResponse;
import animal.dto.ProductResponse.CreateProductRes;
import animal.dto.ProductResponse.GetProductRes;
import animal.infrastructure.CompanyRepository;
import animal.infrastructure.ProductRepository;
import animal.mapper.ProductMapper;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductMapper productMapper;

    @Transactional
    public CreateProductRes createProduct(CreateProductReq createProductReq) {
        Company company = companyRepository.findById(CompanyId.of(createProductReq.companyId()))
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        // 상품 등록 로직
        Product product = productMapper.toProduct(createProductReq, company);

        company.addProduct(product);

        // 상품 등록 후 hub에 등록 요청

        return productMapper.toCreateProductRes(product);
    }

    @Transactional
    public void updateProduct(CompanyId companyId, UpdateProductReq updateProductReq) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        Product product = company.getProducts().stream()
            .filter(p -> p.getId().equals(ProductId.of(updateProductReq.productId())))
            .findFirst()
            .orElseThrow(() -> new GlobalException(ErrorCase.PRODUCT_NOT_FOUND));

        product.update(updateProductReq.name(), updateProductReq.price());

        // 상품 수정 후 hub에 수정 요청
    }

    @Transactional
    public void deleteProduct(CompanyId companyId, ProductId productId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        company.getProducts().stream()
            .filter(p -> p.getId().equals(productId))
            .findFirst()
            // TODO: username 필요
            .ifPresent(product -> product.delete("admin"));

        // 상품 삭제 후 hub에 삭제 요청
    }

    public ProductResponse.GetProductRes getProduct(CompanyId companyId, ProductId productId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        Product product = company.getProducts().stream()
            .filter(p -> p.getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new GlobalException(ErrorCase.PRODUCT_NOT_FOUND));

        return productMapper.toGetProductRes(product);
    }

    public Page<GetProductRes> getProductList(CompanyId companyId, String productName, Pageable pageable) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        // 상품 검색인데 like 검색처럼 동작 page list 반환
        Page<Product> productList = productRepository.findByCompanyAndNameContaining(company, productName, pageable);

        return productList.map(productMapper::toGetProductRes);
    }
}
