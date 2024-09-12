package animal.presentation;

import animal.application.CompanyService;
import animal.domain.CompanyId;
import animal.dto.CompanyRequest;
import animal.dto.CompanyRequest.AddStockReq;
import animal.dto.CompanyRequest.CreateCompanyReq;
import animal.dto.CompanyResponse.AddCompanyRes;
import animal.dto.CompanyResponse.CreateCompanyRes;
import animal.dto.CompanyResponse.GetCompanyRes;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;
import response.CommonResponse.CommonEmptyRes;
import response.CompanyType;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 업체 등록 API
     */
    @PostMapping
    public CommonResponse<CreateCompanyRes> createCompany(@RequestBody CreateCompanyReq createCompanyReq) {
        var createCompanyRes = companyService.createCompany(createCompanyReq);
        return CommonResponse.success(createCompanyRes);
    }

    /**
     * 업체 조회 API
     */
    @GetMapping("/{companyId}")
    public CommonResponse<GetCompanyRes> getCompany(@PathVariable UUID companyId) {
        return CommonResponse.success(companyService.getCompany(CompanyId.of(companyId)));
    }

    /**
     * 업체 수정 API
     */
    @PatchMapping("/{companyId}")
    public CommonResponse<CommonEmptyRes> updateCompany(@PathVariable UUID companyId,
        @RequestBody CompanyRequest.UpdateCompanyReq updateCompanyReq) {
        companyService.updateCompany(CompanyId.of(companyId), updateCompanyReq);
        return CommonResponse.success();
    }

    /**
     * 업체 삭제 API
     */
    @DeleteMapping("/{companyId}")
    public CommonResponse<CommonEmptyRes> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(CompanyId.of(companyId));
        return CommonResponse.success();
    }

    /**
     * 업체 목록 조회 API
     */
    @GetMapping
    public CommonResponse<Page<GetCompanyRes>> getCompanyList(Pageable pageable) {
        return CommonResponse.success(companyService.getCompanyList(pageable));
    }

    /**
     * 업체 검색 API
     */
    @GetMapping("/search")
    public CommonResponse<Page<GetCompanyRes>> searchCompany(
        @RequestParam(required = false) String companyName,
        @RequestParam(required = false) CompanyType companyType,
        Pageable pageable
    ) {
        return CommonResponse.success(companyService.searchCompany(companyName, companyType, pageable));
    }

    /**
     * 재고 추가 API
     */
    @PostMapping("/{companyId}/stocks")
    public AddCompanyRes addStock(@PathVariable UUID companyId, @RequestBody AddStockReq addStockReq) {
        return companyService.addStock(CompanyId.of(companyId), addStockReq);
    }


}
