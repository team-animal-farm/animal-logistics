package animal.application;

import animal.dto.UserRequest.SignUpCompanyReq;
import animal.dto.UserRequest.SignUpDeliveryReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HubFallbackFactory implements FallbackFactory<HubClient> {


  @Override
  public HubClient create(Throwable cause) {
    log.info("fallback due to: " + cause.getMessage(), cause);
    return new HubClient() {

      @Override
      public String createDeliveryUser(SignUpDeliveryReq dto) {
        log.error("delivery user를 hub에서 저장하지 못했습니다. : 허브 배달 담당자 마감");
        return "error에 대한 분기 처리";
      }

      @Override
      public String createCompanyUser(SignUpCompanyReq dto) {
        log.error("delivery user를 hub에서 저장하지 못했습니다. : internal exception");
        return "error에 대한 분기 처리";
      }


    };
  }
}
