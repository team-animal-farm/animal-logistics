package animal.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.domain.Company;
import animal.domain.Product;
import animal.dto.ProductRequest.CreateProductReq;
import animal.dto.ProductResponse.CreateProductRes;
import animal.dto.ProductResponse.GetProductRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

//    GetProductRes toDto(Product product);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "companyId", source = "company.id.id")
    CreateProductRes toCreateProductRes(Product product);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "companyId", source = "company.id.id")
    GetProductRes toGetProductRes(Product product);

    @Mapping(target = "productId", expression = "java(ProductId.ofRandom())")  // 랜덤 ID 생성
    @Mapping(target = "company", source = "company")
    @Mapping(target = "name", source = "createProductReq.name")
    Product toProduct(CreateProductReq createProductReq, Company company);

}