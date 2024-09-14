package animal.auth.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import animal.auth.domain.User;
import animal.auth.dto.UserRequest.SignUpUserReq;
import animal.auth.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface UserMapper {

  User from(SignUpUserReq SignUpUserReq);

  UserResponse.GetUserRes toGetUserResponse(User user, UserResponse.DeliveryUserRes deliveryUserRes);

  UserResponse.GetUserRes toGetUserResponse(User user, UserResponse.CompanyUserRes companyUserRes);

}
