package animal.application;

import animal.domain.Address;
import animal.domain.Company;
import animal.dto.CompanyRequest.CreateCompanyReq;
import animal.infrastructure.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public void createCompany(CreateCompanyReq createCompanyReq) {
        Address address = Address.builder()
            .roadAddress(createCompanyReq.roadAddress())
            .detailAddress(createCompanyReq.detailAddress())
            .zipcode(createCompanyReq.zipcode())
            .build();

        // TODO: 업체 주소와 가장 가까운 허브를 찾아오는 로직 필요
        // var hubId = hubClient.findHub(address);

        Company company = Company.builder()
            .name(createCompanyReq.name())
            .address(address)
            .companyType(createCompanyReq.companyType())
            // TODO: 허브ID 받아오는 로직 처리 후 수정
            .hubId(null)
            .build();

        companyRepository.save(company);
    }
}
