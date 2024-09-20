package animal.auth.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.auth.domain.User;
import animal.auth.dto.UserRequest.ModifyDeliveryUserReq;
import animal.auth.dto.UserRequest.SignUpUserReq;
import animal.auth.dto.UserRequest.UpdateDeliveryUserReq;
import animal.auth.dto.UserResponse.GetDeliveryDriverRes;
import animal.auth.dto.UserResponse.UserRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface UserMapper {

  User from(SignUpUserReq SignUpUserReq);

  default void updateUserFields(User user, UserRes userRes) {
    userRes.updateUserRes(user);
  }

  UpdateDeliveryUserReq toUpdateDeliveryUserReq(ModifyDeliveryUserReq modifyDeliveryUserReq);

    GetDeliveryDriverRes toGetDeliveryDriver(User user);

    UserRes toUserRes(User user);

}
