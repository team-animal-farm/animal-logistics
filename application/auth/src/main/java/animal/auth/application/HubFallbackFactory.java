package animal.auth.application;

import animal.auth.dto.UserRequest.SignUpCompanyReq;
import animal.auth.dto.UserRequest.SignUpDeliveryReq;
import animal.auth.dto.UserRequest.UpdateDeliveryUserReq;
import animal.auth.dto.UserResponse.CompanyUserRes;
import animal.auth.dto.UserResponse.DeliveryUserRes;
import exception.GlobalException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import response.ErrorCase;

@Component
@Slf4j
public class HubFallbackFactory implements FallbackFactory<HubClient> {

  @Override
  public HubClient create(Throwable cause) {
    return new HubClient() {

      @Override
      public void createDeliveryUser(SignUpDeliveryReq dto) {
        log.warn("###Fallback 호출-createDeliveryUser 오류 발생 원인  :{}", cause.getMessage());
        handleFeignException(cause);
      }

      @Override
      public DeliveryUserRes GetDeliveryUserInfo(String username) {
        log.warn("###Fallback 호출-GetDeliveryUserInfo 오류 발생 원인 :{}", cause.getMessage());
        handleFeignException(cause);
        return null;
      }

      @Override
      public void ModifyDeliveryUser(String username, UpdateDeliveryUserReq dto) {
        log.warn("###Fallback 호출-ModifyDeliveryUser 오류 발생 원인 :{}", cause.getMessage());
        handleFeignException(cause);
      }

      @Override
      public void createCompanyUser(SignUpCompanyReq dto) {
        log.warn("###Fallback 호출-createCompanyUser 오류 발생 원인 :{}", cause.getMessage());
        handleFeignException(cause);
      }

      @Override
      public CompanyUserRes GetCompanyUserInfo(String username) {
        log.warn("###Fallback 호출-GetCompanyUserInfo 오류 발생 원인 :{}", cause.getMessage());
        handleFeignException(cause);
        return null;
      }

      @Override
      public void DeleteUser(String username) {
        log.warn("###Fallback 호출-DeleteUser 오류 발생 원인 :{}", cause.getMessage());
        handleFeignException(cause);
      }

      //todo : valid 유효성 검증
      private void handleFeignException(Throwable cause) {
        if (cause instanceof FeignException.NotFound) {
          throw new GlobalException(ErrorCase.USER_NOT_FOUND);
        } else if (cause instanceof FeignException.Conflict) {
          throw new GlobalException(ErrorCase.CAPACITY_FULL);
        } else if (cause instanceof FeignException.BadRequest) {
          throw new GlobalException(ErrorCase.INVALID_INPUT);
        } else {
          log.error("알 수 없는 오류 발생: {}", cause.getMessage());
          throw new GlobalException(ErrorCase.SYSTEM_ERROR);
        }
      }
    };
  }
}
