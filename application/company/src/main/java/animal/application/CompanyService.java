package animal.application;

import animal.domain.Company;
import animal.domain.CompanyId;
import animal.dto.CompanyRequest.CreateCompanyReq;
import animal.dto.CompanyRequest.UpdateCompanyReq;
import animal.dto.CompanyResponse.CreateCompanyRes;
import animal.dto.CompanyResponse.GetCompanyRes;
import animal.infrastructure.CompanyRepository;
import animal.mapper.CompanyMapper;
import exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional
    public CreateCompanyRes createCompany(CreateCompanyReq createCompanyReq) {
        Company company = Company.builder()
            .username(createCompanyReq.username())
            .name(createCompanyReq.name())
            .companyType(createCompanyReq.companyType())
            .address(createCompanyReq.address())
            .build();

        Company save = companyRepository.save(company);

        return companyMapper.toCreateCompanyRes(save);
    }

    public GetCompanyRes getCompany(CompanyId companyId) {
        // TODO: 회원 검증 로직이 필요한지?

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));
        return companyMapper.toDto(company);
    }

    @Transactional
    public void updateCompany(CompanyId companyId, UpdateCompanyReq updateCompanyReq) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        company.updateCompany(updateCompanyReq.name(), updateCompanyReq.companyStatus(), updateCompanyReq.companyType(),
            updateCompanyReq.address());
    }

    @Transactional
    public void deleteCompany(CompanyId companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND));

        company.delete("");
    }

    public List<GetCompanyRes> getCompanyList() {
        List<Company> companyList = companyRepository.findAll();

        return companyList.stream().map(companyMapper::toDto).toList();
    }
}
