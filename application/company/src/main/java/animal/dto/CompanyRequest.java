package animal.dto;

import animal.domain.Address;

public class CompanyRequest {

    public record CreateCompanyReq(
        String username,
        String companyType,
        Address address
    ) {

    }
}
