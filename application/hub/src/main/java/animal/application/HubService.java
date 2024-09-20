package animal.application;

import animal.domain.Hub;
import animal.domain.HubDeliveryManager;
import animal.domain.HubId;
import animal.dto.HubRequest.CreateHubReq;
import animal.dto.HubRequest.UpdateHubReq;
import animal.dto.HubResponse.CreateHubRes;
import animal.dto.HubResponse.GetHubIdList;
import animal.dto.HubResponse.GetHubRes;
import animal.dto.HubResponse.UpdateHubRes;
import animal.infrastructure.HubDeliveryManagerRepository;
import animal.infrastructure.HubRepository;
import animal.mapper.HubMapper;
import exception.GlobalException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "cache:hubs")
public class HubService {

    private final HubMapper hubMapper;
    private final HubRepository hubRepository;
    private final HubDeliveryManagerRepository hubDeliveryManagerRepository;

    /**
     * 허브 단건 조회
     */
    @Cacheable(key = "#hubId.id", unless = "#result == null")
    public GetHubRes getHub(HubId hubId) {
        Hub hub = findHub(hubId);
        List<HubDeliveryManager> hubDeliveryManagerList = hubDeliveryManagerRepository.findAll();
        return hubMapper.toGetHubRes(hub, hubDeliveryManagerList);
    }

    /**
     * 허브 리스트 조회
     */
    @Cacheable(key = "'all'")
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

    public GetHubIdList getHubIdList() {
        // ID만 가져오기
        return GetHubIdList.builder()
            .hubIds(hubRepository.findAll().stream().map(hub -> hub.getId().getId()).toList())
            .build();
    }

    public Map<String, UUID> getHubIdFromCompany(Map<String, UUID> map) {
        // hub에 소속된 company 찾기
        UUID receiveHubId = hubRepository.findByCompanyId(map.get("receiveCompanyId"))
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND))
            .getId().getId();

        UUID providerHubId = hubRepository.findByCompanyId(map.get("providerCompanyId"))
            .orElseThrow(() -> new GlobalException(ErrorCase.COMPANY_NOT_FOUND))
            .getId().getId();

        Map<String, UUID> resultMap = new HashMap<>();
        resultMap.put("receiveHubId", receiveHubId);
        resultMap.put("providerHubId", providerHubId);

        return resultMap;
    }

}
