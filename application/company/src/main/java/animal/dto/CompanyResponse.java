package animal.dto;

import animal.domain.Address;
import java.util.UUID;

public class CompanyResponse {

    public record CreateCompanyRes(
        UUID id
    ) {

    }

    public record GetCompanyRes(
        String username,
        String companyType,
        String companyStatus,
        Address address
    ) {

    }

    public record AddCompanyRes(
        UUID companyId,
        Integer stockQuantity
    ) {

    }
}
