package animal.dto;

public class CompanyRequest {

    public record CreateCompanyReq(
        String name,
        String companyType,
        String roadAddress,
        String detailAddress,
        String zipcode
    ) {

    }
}
