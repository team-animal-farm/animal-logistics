package animal.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.domain.Product;
import animal.dto.ProductResponse.CreateProductRes;
import animal.dto.ProductResponse.GetProductRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

//    GetProductRes toDto(Product product);

    @Mapping(target = "id", source = "id.id")
    CreateProductRes toCreateProductRes(Product product);

    @Mapping(target = "id", source = "id.id")
    GetProductRes toGetProductRes(Product product);
}