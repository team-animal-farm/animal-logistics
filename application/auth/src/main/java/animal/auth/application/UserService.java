package animal.auth.application;

import animal.auth.domain.User;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserRequest.ModifyDeliveryUserReq;
import animal.auth.dto.UserRequest.ModifyUserReq;
import animal.auth.dto.UserRequest.SignInUserReq;
import animal.auth.dto.UserRequest.SignUpCompanyReq;
import animal.auth.dto.UserRequest.SignUpDeliveryReq;
import animal.auth.dto.UserRequest.UpdateDeliveryUserReq;
import animal.auth.dto.UserResponse.UserRes;
import animal.auth.infrastructure.UserRepository;
import animal.auth.mapper.UserMapper;
import exception.GlobalException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import response.ErrorCase;
import security.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    public static final String CIRCUIT_BREAKER_NAME = "hubFeignClient";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HubClient hubClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //todo : 회원가입 단일 요청으로 관리

    //배달 담당자 생성
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void createDeliveryUser(SignUpDeliveryReq dto) {

        String password = passwordEncoder.encode(dto.getPassword());

        checkEmail(dto.getEmail());

        UpdateDeliveryUserReq request = new UpdateDeliveryUserReq(dto.getUsername(), dto.getSlackId());
        hubClient.createDeliveryUser(dto.getHubId(), request);

        User user = userMapper.from(dto);
        user.endCoderPassword(password);
        userRepository.save(user);
    }


    //업체 담당자 생성
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void createCompanyUser(SignUpCompanyReq dto) {

        String password = passwordEncoder.encode(dto.getPassword());

        checkEmail(dto.getEmail());

        hubClient.createCompanyUser(dto.getHubId(), dto);

        User user = userMapper.from(dto);
        user.endCoderPassword(password);
        userRepository.save(user);
    }

    //로그인
    public String loginUser(SignInUserReq dto) {

        User user = findUserByUsername(dto.username());

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new GlobalException(ErrorCase.USER_NOT_FOUND);
        }

        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }


    //사용자 상세 조회
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public UserRes getUserInfo(String username) {

        User user = findUserByUsername(username);

        UserRes response = user.getRole().isDeliveryRole() ? hubClient.getDeliveryUserInfo(user.getUsername())
            : hubClient.getCompanyUserInfo(user.getUsername());

        userMapper.updateUserFields(user, response);
        return response;
    }

    //사용자 리스트 조회
    public Page<UserRes> getUserList(Pageable pageable, String username) {

        User user = findUserByUsername(username);

        Page<User> userPage = userRepository.findAllByHubId(user.getHubId(), pageable);
        return userPage.map(userMapper::toUserRes);
    }

    //사용자 정보 수정
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void modifyUser(String username, ModifyUserReq dto) {

        User user = findUserByUsername(username);

        if (user.getRole().isDeliveryRole()) {
            UserRequest.UpdateDeliveryUserReq request = userMapper.toUpdateDeliveryUserReq((ModifyDeliveryUserReq) dto);
            hubClient.modifyDeliveryUser(username, request);
        }

        user.updateInfo(dto);
    }

    //todo : 비동기통신으로 리팩토링
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "handleFallback")
    public void deleteUser(String username) {
        User user = findUserByUsername(username);

        hubClient.deleteUser(username);

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

}
