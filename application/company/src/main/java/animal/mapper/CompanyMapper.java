package animal.mapper;

import static animal.dto.CompanyResponse.CreateCompanyRes;
import static animal.dto.CompanyResponse.GetCompanyRes;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.domain.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface CompanyMapper {


    GetCompanyRes toDto(Company company);

    @Mapping(target = "id", source = "id.id")
    CreateCompanyRes toCreateCompanyRes(Company company);
}