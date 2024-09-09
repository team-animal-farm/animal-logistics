package animal.presentation;

import animal.application.HubService;
import animal.domain.HubId;
import animal.dto.HubRequest.CreateHubReq;
import animal.dto.HubResponse.CreateHubRes;
import animal.dto.HubResponse.GetHubRes;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CommonResponse;

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
        GetHubRes hub = hubService.getHub(HubId.of(hubId));
        return CommonResponse.success(hub);
    }

    /**
     * 허브 리스트 조회
     */
    @GetMapping
    public CommonResponse<List<GetHubRes>> getHubList() {
        List<GetHubRes> hubList = hubService.getHubList();
        return CommonResponse.success(hubList);
    }

    /**
     * 허브 생성
     */
    @PostMapping
    public CommonResponse<CreateHubRes> createHub(@Validated @RequestBody CreateHubReq createHubReq) {
        CreateHubRes hub = hubService.createHub(createHubReq);
        return CommonResponse.success(hub);
    }
}
