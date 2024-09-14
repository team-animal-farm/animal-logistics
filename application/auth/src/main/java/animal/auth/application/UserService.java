package animal.auth.application;

import animal.auth.domain.User;
import animal.auth.dto.UserRequest;
import animal.auth.dto.UserRequest.ModifyUserReq;
import animal.auth.dto.UserResponse;
import animal.auth.infrastructure.UserRepository;
import animal.auth.mapper.UserMapper;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;
import security.JwtUtil;
import security.UserRole;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtUtil jwtUtil;
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

  public void createCompanyUser(UserRequest.SignUpCompanyReq dto) {

    //encodePassword.encodingPassword(passwordEncoder.encode(dto.getPassword()));

    checkEmail(dto.getEmail());

    hubClient.createCompanyUser(dto);

    User user = userMapper.from(dto);
    userRepository.save(user);
  }

  private void checkEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new GlobalException(ErrorCase.DUPLICATE_EMAIL);
    }
  }

  public String createAccessToken(String email) {
    User user = userRepository.findById(email)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
    return jwtUtil.createToken(user.getEmail(), user.getRole());
  }

  //사용자 상세 조회
  public UserResponse.GetUserRes getUserInfo(String username) {

    User user = userRepository.findById(username)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));

    //todo : company와 delivery를 서브 클래스로 두는 슈퍼클래스로 관리하도록 변경
    UserRole role = user.getRole();
    if (role.toString().startsWith("COMPANY")) {
      UserResponse.CompanyUserRes response = hubClient.GetCompanyUserInfo(role);
      return userMapper.toGetUserResponse(user, response);
    } else {
      UserResponse.DeliveryUserRes response = hubClient.GetDeliveryUserInfo(role);
      hubClient.GetDeliveryUserInfo(role);
      return userMapper.toGetUserResponse(user, response);
    }
  }

  public void getUserList(Pageable pageable) {

  }

  @Transactional
  public void modifyDeliveryUser(String username, UserRequest.ModifyDeliveryUserReq dto) {

    /**
     * 허브 변경,배달 타입 변경,슬랙 아이디 변경이 있지만
     * 슬랙 아이디 변경만 가능하도록 만듬
     */
    User user = userRepository.findById(username)
        .orElseThrow((() -> new GlobalException(ErrorCase.USER_NOT_FOUND)));

    //todo : slackid를 변경했는지 redis로 먼저 확인
    hubClient.ModifySlackId(username);
    user.updateInfo((ModifyUserReq) dto);

  }

  public void modifyCompanyUser(String username, UserRequest.ModifyUserReq dto) {

    /**
     * 업체 변경 시
     */
    User user = userRepository.findById(username)
        .orElseThrow((() -> new GlobalException(ErrorCase.USER_NOT_FOUND)));

    user.updateInfo((ModifyUserReq) dto);


  }

  public void deleteUser(String username) {
    //todo : 비동기 통신으로?
    //내부 통신 - 데이터 삭제 요청
    User user = userRepository.findById(username)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
    user.delete(username);
  }


}
