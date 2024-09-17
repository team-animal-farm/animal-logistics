package animal.auth.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import animal.auth.application.UserService;
import animal.auth.domain.DeliveryType;
import animal.auth.domain.User;
import animal.auth.dto.UserResponse.DeliveryUserRes;
import animal.auth.dto.UserResponse.UserRes;
import animal.auth.mapper.UserMapper;
import animal.auth.mapper.UserMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import security.UserRole;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @MockBean
  UserService userService;

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .build();
  }

  @Test
  @DisplayName("배달담당자 정보 요청")
  void test1() throws Exception {

    //given
    User user = User.builder().username("wywuwdi").role(UserRole.DELIVERY_COMPANY).build();
    DeliveryUserRes deliveryUserRes = new DeliveryUserRes(DeliveryType.COMPANY, "slackId");
    deliveryUserRes.setHubName("강원");

    UserRes userRes = deliveryUserRes;
    UserMapper mapper = new UserMapperImpl();
    mapper.updateUserFields(user, userRes);

    given(userService.getUserInfo("wywudi")).willReturn(deliveryUserRes);

    //when
    mvc.perform(get("/users/wywudi"))
        .andExpect(status().isOk())
        .andDo(print());
  }


}