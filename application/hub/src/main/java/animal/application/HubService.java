package animal.application;

import animal.domain.Hub;
import animal.domain.HubId;
import animal.dto.HubRequest.CreateHubReq;
import animal.dto.HubResponse.CreateHubRes;
import animal.dto.HubResponse.GetHubRes;
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

    /**
     * 허브 단건 조회
     */
    public GetHubRes getHub(HubId hubId) {
        Hub hub = findHub(hubId);
        return hubMapper.toGetHubRes(hub);
    }

    /**
     * 허브 리스트 조회
     */
    public List<GetHubRes> getHubList() {
        List<Hub> hubList = hubRepository.findAll();
        return hubMapper.toGetHubResList(hubList);
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

    private Hub findHub(HubId hubId) {
        return hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ErrorCase.NOT_FOUND));
    }
}
