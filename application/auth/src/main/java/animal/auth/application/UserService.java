package animal.auth.application;

import animal.auth.domain.User;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserRequest.ModifyDeliveryUserReq;
import animal.auth.dto.UserRequest.ModifyUserReq;
import animal.auth.dto.UserResponse.GetDeliveryDriverRes;
import animal.auth.dto.UserResponse.UserRes;
import animal.auth.infrastructure.UserRepository;
import animal.auth.mapper.UserMapper;
import exception.GlobalException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import response.ErrorCase;
import security.UserRole;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    public static final String CIRCUIT_BREAKER_NAME = "hubFeignClient";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HubClient hubClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;


    //배달 담당자 생성
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void createDeliveryUser(UserRequest.SignUpDeliveryReq dto) {

        //encodePassword.encodingPassword(passwordEncoder.encode(dto.getPassword()));

        checkEmail(dto.getEmail());

        hubClient.createDeliveryUser(dto);

        User user = userMapper.from(dto);
        userRepository.save(user);
    }


    //업체 담당자 생성
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void createCompanyUser(UserRequest.SignUpCompanyReq dto) {

        //encodePassword.encodingPassword(passwordEncoder.encode(dto.getPassword()));

        checkEmail(dto.getEmail());

        hubClient.createCompanyUser(dto);

        User user = userMapper.from(dto);
        userRepository.save(user);
    }


    //사용자 상세 조회
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public UserRes getUserInfo(String username) {

        User user = findUserByUsername(username);

        UserRes response = user.getRole().isDeliveryRole() ? hubClient.GetDeliveryUserInfo(user.getUsername())
            : hubClient.GetCompanyUserInfo(user.getUsername());

        userMapper.updateUserFields(user, response);
        return response;
    }

    public void getUserList(Pageable pageable) {

    }

    //사용자 정보 수정
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void modifyUser(String username, ModifyUserReq dto) {

        User user = findUserByUsername(username);

        if (user.getRole().isDeliveryRole()) {
            UserRequest.UpdateDeliveryUserReq request = userMapper.toUpdateDeliveryUserReq((ModifyDeliveryUserReq) dto);
            hubClient.ModifyDeliveryUser(username, request);
        }

        user.updateInfo(dto);
    }

    //todo : 비동기통신으로 리팩토링
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void deleteUser(String username) {
        User user = findUserByUsername(username);

        hubClient.DeleteUser(username);

        user.delete(username);
    }

    private void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new GlobalException(ErrorCase.DUPLICATE_EMAIL);
        }
    }

    private User findUserByUsername(String username) {
        return userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
    }


    @PostConstruct
    public void registerEventListener() {
        circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME).getEventPublisher()
            .onStateTransition(e -> log.info("#####Fallback 상태 전환 {}", e))
            .onFailureRateExceeded(e -> log.info("#####Fallback 실패율 초과 : {}", e))
            .onCallNotPermitted(e -> log.info("#####Fallback 호출 차단 : {}", e))
            .onError(e -> log.info("#####Fallback 오류 발생 : {}", e));
    }

    public void handleFallback(Throwable t) {
        log.error("###FallbackMethod : 요청 실패원인은{}", t.getMessage());
        throw new GlobalException(ErrorCase.HUB_SERVICE_FAILURE);
    }

    public List<GetDeliveryDriverRes> getDeliveryDriver(UUID hubId, UserRole userRole) {
        List<User> driverList = userRepository.findByHubIdAndRole(hubId, userRole);
        return driverList.stream().map(userMapper::toGetDeliveryDriver).toList();
    }
}

