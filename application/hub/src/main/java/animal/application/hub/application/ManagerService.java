package animal.application.hub.application;

import animal.application.hub.domain.hub.Hub;
import animal.application.hub.domain.manager.HubDeliveryManager;
import animal.application.hub.domain.hub.HubId;
import animal.application.hub.domain.manager.CompanyDeliveryManager;
import animal.application.hub.domain.manager.HubManager;
import animal.application.hub.domain.manager.ProviderCompanyManager;
import animal.application.hub.domain.manager.SlackId;
import animal.application.hub.domain.manager.Username;
import animal.application.hub.dto.HubResponse.GetHubRes;
import animal.application.hub.dto.ManagerRequest.AddCompanyDeliveryManagerReq;
import animal.application.hub.dto.ManagerRequest.AddHubDeliveryManagerReq;
import animal.application.hub.dto.ManagerRequest.AddHubManagerReq;
import animal.application.hub.dto.ManagerRequest.AddProviderCompanyManagerReq;
import animal.application.hub.infrastructure.HubDeliveryManagerRepository;
import animal.application.hub.infrastructure.HubRepository;
import animal.application.hub.mapper.HubMapper;
import exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ManagerService {

    private final HubMapper hubMapper;
    private final HubRepository hubRepository;
    private final HubDeliveryManagerRepository hubDeliveryManagerRepository;

    /**
     * 허브 관리자 추가
     */
    public GetHubRes addHubManager(HubId hubId, AddHubManagerReq request) {

        Hub hub = findHub(hubId);

        HubManager hubManager = HubManager.builder()
            .username(Username.of(request.username()))
            .build();

        hub.addHubManager(hubManager);
        List<HubDeliveryManager> hubDeliveryManagerList = hubDeliveryManagerRepository.findAll();

        return hubMapper.toGetHubRes(hub, hubDeliveryManagerList);
    }

    /**
     * 허브 배송 관리자 추가
     */
    public void addHubDeliveryManager(AddHubDeliveryManagerReq request) {

        HubDeliveryManager hubDeliveryManager = HubDeliveryManager.builder()
            .username(request.username())
            .slackId(request.slackId())
            .build();

        hubDeliveryManagerRepository.save(hubDeliveryManager);
    }

    /**
     * 업체 배송 관리자 추가
     */
    public GetHubRes addCompanyDeliveryManager(HubId hubId, AddCompanyDeliveryManagerReq request) {

        Hub hub = findHub(hubId);

        CompanyDeliveryManager companyDeliveryManager = CompanyDeliveryManager.builder()
            .username(Username.of(request.username()))
            .slackId(SlackId.of(request.slackId()))
            .build();

        hub.addCompanyDeliveryManager(companyDeliveryManager);
        List<HubDeliveryManager> hubDeliveryManagerList = hubDeliveryManagerRepository.findAll();

        return hubMapper.toGetHubRes(hub, hubDeliveryManagerList);
    }

    /**
     * 공급 업체 관리자 추가
     */
    public GetHubRes addProviderCompanyManager(HubId hubId, AddProviderCompanyManagerReq request) {

        Hub hub = findHub(hubId);

        ProviderCompanyManager providerCompanyManager = ProviderCompanyManager.builder()
            .username(Username.of(request.username()))
            .slackId(SlackId.of(request.slackId()))
            .companyId(request.companyId())
            .build();

        hub.addProviderCompanyManager(providerCompanyManager);
        List<HubDeliveryManager> hubDeliveryManagerList = hubDeliveryManagerRepository.findAll();

        return hubMapper.toGetHubRes(hub, hubDeliveryManagerList);
    }

    private Hub findHub(HubId hubId) {
        return hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ErrorCase.HUB_NOT_FOUND));
    }
}
