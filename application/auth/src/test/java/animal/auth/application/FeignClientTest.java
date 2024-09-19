package animal.auth.application;

import static org.mockito.BDDMockito.given;
import animal.auth.domain.CompanyType;
import animal.auth.domain.DeliveryType;
import animal.auth.domain.User;
import animal.auth.dto.UserResponse.CompanyUserRes;
import animal.auth.dto.UserResponse.DeliveryUserRes;
import animal.auth.dto.UserResponse.UserRes;
import animal.auth.infrastructure.UserRepository;
import animal.auth.mapper.UserMapper;
import animal.auth.mapper.UserMapperImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import security.UserRole;

@ExtendWith(MockitoExtension.class)
public class FeignClientTest {

    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Mock
    HubClient hubClient;

    @Test
    @DisplayName("배달담당자 정보 요청")
    void test1() {
        String username = "username";

        UserMapper userMapper = new UserMapperImpl();

        UserService userService = new UserService(userRepository, userMapper, hubClient);

        User user = User.builder().username(username).role(UserRole.DELIVERY_COMPANY).build();
        given(userRepository.findById(username)).willReturn(Optional.of(user));
        DeliveryUserRes deliveryUserRes = new DeliveryUserRes(DeliveryType.COMPANY, "slackId");
        deliveryUserRes.setHubName("강원");

        given(hubClient.getDeliveryUserInfo(username)).willReturn(deliveryUserRes);

        UserRes response = userService.getUserInfo(username);
        System.out.println("response: " + (response).toString());

    }

    @Test
    @DisplayName("업체담당자 정보 요청")
    void test2() {
        String username = "username";

        UserMapper userMapper = new UserMapperImpl();

        UserService userService = new UserService(userRepository, userMapper, hubClient);

        User user = User.builder().username(username).role(UserRole.COMPANY_RECEIVE).build();
        given(userRepository.findById(username)).willReturn(Optional.of(user));
        CompanyUserRes companyUserRes = new CompanyUserRes(CompanyType.RECEIVE);
        companyUserRes.setHubName("강원");

        given(hubClient.getCompanyUserInfo(username)).willReturn(companyUserRes);

        UserRes response = userService.getUserInfo(username);
        System.out.println("response: " + (response).toString());

    }


}