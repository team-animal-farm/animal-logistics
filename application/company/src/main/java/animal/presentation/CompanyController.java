package animal.presentation;

import animal.application.CompanyService;
import animal.dto.CompanyRequest.CreateCompanyReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;

@RestController
@RequestMapping("/companys")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 업체 생성 API
     */
    @PostMapping
    public CommonResponse<?> createCompany(@RequestBody CreateCompanyReq createCompanyReq) {
        companyService.createCompany(createCompanyReq);
        return CommonResponse.success();
    }

}
