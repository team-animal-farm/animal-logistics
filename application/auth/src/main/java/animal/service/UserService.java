package animal.service;

import animal.dto.SampleResponse;
import animal.mapper.UserMapper;
import animal.infrastructure.UserRepository;
import animal.dto.SampleRequest;
import animal.domain.User;
import animal.utils.JwtUtil;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import response.ErrorCase;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(SampleRequest.SignUpUserReq request) {
        //todo : 회원 권한 제어를 어떻게 관리할건지

        //password 암호화 gateway에서 처리
        //request.encodingPassword(passwordEncoder.encode(request.password()));

        if (checkEmail(request.email())) {
            throw new GlobalException(ErrorCase.DUPLICATE_EMAIL);
        }

        User user = userMapper.from(request);
        userRepository.save(user);
    }

    public String createAccessToken(String email) {
        User user = userRepository.findByEmail(email);
        return jwtUtil.createToken(user.getEmail(), user.getRole());
    }

    private boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public SampleResponse.getUserResponse getUserInfo(String username) {
        return userRepository.findById(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new GlobalException(ErrorCase.USER_NOT_FOUND));

    }

}
