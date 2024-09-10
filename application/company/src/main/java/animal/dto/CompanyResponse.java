package animal.dto;

import animal.domain.Address;

public class CompanyResponse {

    public record GetCompanyRes(
        String username,
        String companyType,
        String companyStatus,
        Address address
    ) {

    }
}
