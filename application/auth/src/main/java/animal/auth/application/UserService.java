package animal.auth.application;

import animal.auth.domain.User;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserRequest.ModifyDeliveryUserReq;
import animal.auth.dto.UserRequest.ModifyUserReq;
import animal.auth.dto.UserResponse.UserRes;
import animal.auth.infrastructure.UserRepository;
import animal.auth.mapper.UserMapper;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final HubClient hubClient;


  //배달 담당자 생성
  public void createDeliveryUser(UserRequest.SignUpDeliveryReq dto) {

    //encodePassword.encodingPassword(passwordEncoder.encode(dto.getPassword()));

    checkEmail(dto.getEmail());

    hubClient.createDeliveryUser(dto);

    User user = userMapper.from(dto);
    userRepository.save(user);
  }

  //업체 담당자 생성
  public void createCompanyUser(UserRequest.SignUpCompanyReq dto) {

    //encodePassword.encodingPassword(passwordEncoder.encode(dto.getPassword()));

    checkEmail(dto.getEmail());

    hubClient.createCompanyUser(dto);

    User user = userMapper.from(dto);
    userRepository.save(user);
  }


  //사용자 상세 조회
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
  public void modifyUser(String username, ModifyUserReq dto) {

    User user = findUserByUsername(username);

    if (user.getRole().isDeliveryRole()) {
      UserRequest.UpdateDeliveryUserReq request = userMapper.toUpdateDeliveryUserReq((ModifyDeliveryUserReq) dto);
      hubClient.ModifyDeliveryUser(username, request);
    }

    user.updateInfo(dto);
  }

  //todo : 비동기통신으로 리팩토링
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

}
