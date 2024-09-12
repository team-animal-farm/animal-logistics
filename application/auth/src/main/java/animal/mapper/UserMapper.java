package animal.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.domain.User;
import animal.dto.UserRequest;
import animal.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface UserMapper {

  User from(UserRequest.SignUpUserReq SignUpUserReq);

  //User from(UserRequest.SignUpDeliveryReq signUpDeliveryReq);

  UserResponse.GetUserRes toGetUserResponse(User user);
}
