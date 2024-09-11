package animal.dto;

import animal.domain.Address;

public class CompanyRequest {

    public record CreateCompanyReq(
        String username,
        String name,
        String companyType,
        Address address
    ) {

    }

    public record UpdateCompanyReq(
        String name,
        String companyStatus,
        String companyType,
        Address address
    ) {

    }

    public record AddStockReq(
        Integer stockQuantity,
        Integer requiredQuantity
    ) {

    }
}
