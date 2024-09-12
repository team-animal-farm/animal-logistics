package animal.presentation;

import animal.application.ManagerService;
import animal.domain.HubId;
import animal.dto.HubResponse.GetHubRes;
import animal.dto.ManagerRequest.AddCompanyDeliveryManagerReq;
import animal.dto.ManagerRequest.AddHubDeliveryManagerReq;
import animal.dto.ManagerRequest.AddHubManagerReq;
import animal.dto.ManagerRequest.AddProviderCompanyManagerReq;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs/{hubId}/managers")
public class ManagerController {

    private final ManagerService managerService;

    /**
     * 허브 관리자 추가
     */
    @PostMapping("/hub")
    public CommonResponse<GetHubRes> addHubManager(
        @PathVariable("hubId") UUID hubId,
        @RequestBody AddHubManagerReq request
    ) {
        GetHubRes response = managerService.addHubManager(HubId.of(hubId), request);
        return CommonResponse.success(response);
    }

    /**
     * 허브 배송 관리자 추가
     */
    @PostMapping("/hub-delivery")
    public CommonResponse<GetHubRes> addHubDeliveryManager(
        @PathVariable("hubId") UUID hubId,
        @RequestBody AddHubDeliveryManagerReq request
    ) {
        GetHubRes response = managerService.addHubDeliveryManager(HubId.of(hubId), request);
        return CommonResponse.success(response);
    }

    /**
     * 업체 배송 관리자 추가
     */
    @PostMapping("/company-delivery")
    public CommonResponse<GetHubRes> addCompanyDeliveryManager(
        @PathVariable("hubId") UUID hubId,
        @RequestBody AddCompanyDeliveryManagerReq request
    ) {
        GetHubRes response = managerService.addCompanyDeliveryManager(HubId.of(hubId), request);
        return CommonResponse.success(response);
    }

    /**
     * 업체 관리자 추가
     */
    @PostMapping("/provider-company")
    public CommonResponse<GetHubRes> addCompanyManager(
        @PathVariable("hubId") UUID hubId,
        @RequestBody AddProviderCompanyManagerReq request
    ) {
        GetHubRes response = managerService.addProviderCompanyManager(HubId.of(hubId), request);
        return CommonResponse.success(response);
    }
}
