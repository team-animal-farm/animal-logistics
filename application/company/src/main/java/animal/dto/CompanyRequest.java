package animal.dto;

import animal.domain.Address;
import response.CompanyStatus;
import response.CompanyType;

public class CompanyRequest {

    public record CreateCompanyReq(
        String username,
        String name,
        CompanyType companyType,
        Address address
    ) {

    }

    public record UpdateCompanyReq(
        String name,
        CompanyStatus companyStatus,
        CompanyType companyType,
        Address address
    ) {

    }

    public record AddStockReq(
        Integer stockQuantity,
        Integer requiredQuantity
    ) {

    }
}
