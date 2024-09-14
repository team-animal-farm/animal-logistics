package animal.dto;

import animal.domain.Address;
import java.util.UUID;
import response.CompanyStatus;
import response.CompanyType;

public class CompanyResponse {

    public record CreateCompanyRes(
        UUID id
    ) {

    }

    public record GetCompanyRes(
        String username,
        String name,
        CompanyType companyType,
        CompanyStatus companyStatus,
        Address address
    ) {

    }

    public record AddCompanyRes(
        UUID companyId,
        Integer stockQuantity
    ) {

    }
}
