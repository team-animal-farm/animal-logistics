package animal.application.hub.presentation;

import animal.application.hub.application.HubService;
import animal.application.hub.domain.hub.HubId;
import animal.application.hub.dto.HubRequest.CreateHubReq;
import animal.application.hub.dto.HubRequest.UpdateHubReq;
import animal.application.hub.dto.HubResponse;
import animal.application.hub.dto.HubResponse.CreateHubRes;
import animal.application.hub.dto.HubResponse.GetHubRes;
import animal.application.hub.dto.HubResponse.UpdateHubRes;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;
import response.CommonResponse.CommonEmptyRes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs")
public class HubController {

    private final HubService hubService;

    /**
     * 허브 단건 조회
     */
    @GetMapping("/{hubId}")
    public CommonResponse<GetHubRes> getHub(@PathVariable("hubId") UUID hubId) {
        GetHubRes response = hubService.getHub(HubId.of(hubId));
        return CommonResponse.success(response);
    }

    /**
     * 허브 리스트 조회
     */
    @GetMapping
    public CommonResponse<List<GetHubRes>> getHubList() {
        List<GetHubRes> response = hubService.getHubList();
        return CommonResponse.success(response);
    }


    /**
     * 허브 생성
     */
    @PostMapping
    public CommonResponse<CreateHubRes> createHub(@Validated @RequestBody CreateHubReq createHubReq) {
        CreateHubRes response = hubService.createHub(createHubReq);
        return CommonResponse.success(response);
    }

    /**
     * 허브 수정
     */
    @PatchMapping("/{hubId}")
    public CommonResponse<UpdateHubRes> updateHub(
        @PathVariable("hubId") UUID hubId,
        @Validated @RequestBody UpdateHubReq updateHubReq
    ) {
        UpdateHubRes response = hubService.updateHub(HubId.of(hubId), updateHubReq);
        return CommonResponse.success(response);
    }

    /**
     * 허브 삭제
     */
    @DeleteMapping("/{hubId}")
    public CommonResponse<CommonEmptyRes> deleteHub(@PathVariable("hubId") UUID hubId) {
        hubService.deleteHub(HubId.of(hubId));
        return CommonResponse.success();
    }

    @GetMapping("/idList")
    public HubResponse.GetHubIdList getHubIdList() {
        return hubService.getHubIdList();
    }

    /**
     * 허브 아이디 반환
     */
    @PostMapping("/hubId")
    public Map<String, UUID> getHubId(@RequestBody Map<String, UUID> map) {
        log.info("허브 아이디 반환");
        return hubService.getHubIdFromCompany(map);
    }
}
