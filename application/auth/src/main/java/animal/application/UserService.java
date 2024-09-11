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
import response.ErrorCase;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public void createDeliveryUser(UserRequest.SignUpDeliveryReq dto) {

    //security 적용해야하니까 user에서 추가
    //request.encodingPassword(passwordEncoder.encode(request.password()));

    checkEmail(dto.getEmail());

    //내부 통신

    User user = userMapper.from(dto);
    userRepository.save(user);
  }

  public void createCompanyUser(UserRequest.SignUpCompanyReq dto) {
    checkEmail(dto.getEmail());

    //내부 통신

    User user = userMapper.from(dto);
    userRepository.save(user);
  }

  private void checkEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new GlobalException(ErrorCase.DUPLICATE_EMAIL);
    }
  }

  public String createAccessToken(String email) {
    User user = userRepository.findByEmail(email);
    return jwtUtil.createToken(user.getEmail(), user.getRole());
  }


  public UserResponse.GetUserRes getUserInfo(String username) {
    return userRepository.findById(username)
        //todo : hub이름이 필요함 feignClient로 데이터 받아서 hub타입을 String으로 변경
        .map(userMapper::toGetUserResponse)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
  }

  public void getUserList(Pageable pageable) {

  }

  public void modifyDeliveryUser(String username, UserRequest.ModifyDeliveryUserReq dto) {

    User user = userRepository.findById(username)
        .orElseThrow((() -> new GlobalException(ErrorCase.USER_NOT_FOUND)));

    user.updateInfo((ModifyUserReq) dto);

    //내부 통신

  }


  public void modifyCompanyUser(String username, UserRequest.ModifyUserReq dto) {

    User user = userRepository.findById(username)
        .orElseThrow((() -> new GlobalException(ErrorCase.USER_NOT_FOUND)));

    user.updateInfo((ModifyUserReq) dto);

    //내부 통신

  }

  public void deleteUser(String username) {
    //todo : 회원 탈퇴 시 관련 정보를 모두 삭제 message broker로 보상 트랜잭션 로직 요청
    User user = userRepository.findById(username)
        .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));
    user.markDeleted(username);
  }


}
