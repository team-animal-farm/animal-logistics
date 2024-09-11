package animal.mapper;

import animal.dto.SampleRequest;
import animal.domain.User;
import animal.dto.SampleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User from(SampleRequest.SignUpUserReq SignUpUserReq);

    SampleResponse.getUserResponse toDto(User user);
}
