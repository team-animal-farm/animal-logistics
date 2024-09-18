package animal.application;

import animal.domain.Hub;
import animal.domain.HubDeliveryManager;
import animal.domain.HubId;
import animal.domain.manager.CompanyDeliveryManager;
import animal.domain.manager.HubManager;
import animal.domain.manager.ProviderCompanyManager;
import animal.domain.manager.SlackId;
import animal.domain.manager.Username;
import animal.dto.HubResponse.GetHubRes;
import animal.dto.ManagerRequest.AddCompanyDeliveryManagerReq;
import animal.dto.ManagerRequest.AddHubDeliveryManagerReq;
import animal.dto.ManagerRequest.AddHubManagerReq;
import animal.dto.ManagerRequest.AddProviderCompanyManagerReq;
import animal.infrastructure.HubDeliveryManagerRepository;
import animal.infrastructure.HubRepository;
import animal.mapper.HubMapper;
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
