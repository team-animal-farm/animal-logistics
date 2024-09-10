package animal.mapper;

import animal.domain.Company;
import animal.dto.CompanyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {


    CompanyResponse.GetCompanyRes toDto(Company company);
}