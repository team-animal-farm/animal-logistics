package animal.application;

import animal.domain.User;
import animal.dto.UserRequest;
import animal.dto.UserRequest.ModifyUserReq;
import animal.dto.UserResponse;
import animal.infrastructure.UserRepository;
import animal.mapper.UserMapper;
import animal.utils.JwtUtil;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final HubClient hubClient;

  //배달 담당자 생성
  public void createDeliveryUser(UserRequest.SignUpDeliveryReq dto) {

    //security 적용해야하니까 user에서 추가
    //request.encodingPassword(passwordEncoder.encode(request.password()));

    checkEmail(dto.getEmail());

    //내부 통신 - hub에 delivery 사용자 등록 요청
    hubClient.createDeliveryUser(dto);

    User user = userMapper.from(dto);
    userRepository.save(user);
  }

  public void createCompanyUser(UserRequest.SignUpCompanyReq dto) {
    checkEmail(dto.getEmail());

    //내부 통신 - hub에 company 사용자 등록 요청
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


  public UserResponse.GetUserRes getUserInfo(String username) {

    //내부 통신 - hub 사용자 데이터 요청

    return userRepository.findById(username)

        .map(userMapper::toGetUserResponse)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
  }

  public void getUserList(Pageable pageable) {

  }

  @Transactional
  public void modifyDeliveryUser(String username, UserRequest.ModifyDeliveryUserReq dto) {

    /**
     * 허브 변경
     * 배달 타입 변경
     *슬랙 아이디 변경
     */

    //내부 통신 -
    User user = userRepository.findById(username)
        .orElseThrow((() -> new GlobalException(ErrorCase.USER_NOT_FOUND)));

    user.updateInfo((ModifyUserReq) dto);


  }

  public void modifyCompanyUser(String username, UserRequest.ModifyUserReq dto) {

    //내부 통신
    User user = userRepository.findById(username)
        .orElseThrow((() -> new GlobalException(ErrorCase.USER_NOT_FOUND)));

    user.updateInfo((ModifyUserReq) dto);


  }

  public void deleteUser(String username) {
    //todo : 동기 통신으로 개발하고 비동기 통신으로 리팩토링
    //내부 통신 - 데이터 삭제 요청
    User user = userRepository.findById(username)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
    user.markDeleted(username);
  }


}
