package animal.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryType {

  COMPANY(Type.COMPANY),
  HUB(Type.HUB);

  private final String type;

  public static class Type {

    public static final String COMPANY = "COMPANY_DELIVERY";
    public static final String HUB = "HUB_DELIVERY";

  }
}
