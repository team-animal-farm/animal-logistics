package animal.application;

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

        Company company = Company.builder()
            .username(createCompanyReq.username())
            .companyType(createCompanyReq.companyType())
            .address(createCompanyReq.address())
            .build();

        companyRepository.save(company);
    }
}
