package security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

  COMPANY_SUPPLY(Authority.COMPANY_SUPPLY),
  COMPANY_RECEIVE(Authority.COMPANY_RECEIVE),
  DELIVER_HUB(Authority.DELIVERY_HUB),
  DELIVERY_COMPANY(Authority.DELIVERY_COMPANY),
  MANAGER(Authority.MANAGER),
  MASTER(Authority.MASTER);


  private final String authority;

  public boolean isCompanyRole() {
    return authority.startsWith("COMPANY");
  }

  public static class Authority {

    public static final String COMPANY_SUPPLY = "ROLE_COMPANY_SUPPLY";
    public static final String COMPANY_RECEIVE = "ROLE_COMPANY_RECEIVE";
    public static final String DELIVERY_COMPANY = "ROLE_DELIVERY";
    public static final String DELIVERY_HUB = "ROLE_DELIVERY";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String MASTER = "ROLE_MASTER";
  }
}
