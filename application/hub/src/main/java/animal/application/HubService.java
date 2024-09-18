package animal.application;

import animal.domain.Hub;
import animal.domain.HubDeliveryManager;
import animal.domain.HubId;
import animal.dto.HubRequest.CreateHubReq;
import animal.dto.HubRequest.UpdateHubReq;
import animal.dto.HubResponse.CreateHubRes;
import animal.dto.HubResponse.GetHubRes;
import animal.dto.HubResponse.UpdateHubRes;
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
@Transactional(readOnly = true)
public class HubService {

    private final HubMapper hubMapper;
    private final HubRepository hubRepository;
    private final HubDeliveryManagerRepository hubDeliveryManagerRepository;

    /**
     * 허브 단건 조회
     */
    public GetHubRes getHub(HubId hubId) {
        Hub hub = findHub(hubId);
        List<HubDeliveryManager> hubDeliveryManagerList = hubDeliveryManagerRepository.findAll();
        return hubMapper.toGetHubRes(hub, hubDeliveryManagerList);
    }

    /**
     * 허브 리스트 조회
     */
    public List<GetHubRes> getHubList() {
        
        return hubRepository.findAll()
            .stream()
            .map(hub -> hubMapper.toGetHubRes(hub, hubDeliveryManagerRepository.findAll()))
            .toList();
    }

    /**
     * 허브 생성
     */
    @Transactional
    public CreateHubRes createHub(CreateHubReq createHubReq) {

        Hub hub = hubMapper.toHub(createHubReq);
        Hub savedHub = hubRepository.save(hub);

        return hubMapper.toCreateHubRes(savedHub);
    }

    /**
     * 허브 수정
     */
    @Transactional
    public UpdateHubRes updateHub(HubId hubId, UpdateHubReq updateHubReq) {

        Hub hub = findHub(hubId);
        hub.updateHubInfo(updateHubReq.address(), updateHubReq.coordinate());

        return hubMapper.toUpdateHubRes(hub);
    }

    /**
     * 허브 삭제
     */
    @Transactional
    public void deleteHub(HubId hubId) {
        Hub hub = findHub(hubId);
        hubRepository.delete(hub);
    }

    private Hub findHub(HubId hubId) {
        return hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ErrorCase.HUB_NOT_FOUND));
    }
}
