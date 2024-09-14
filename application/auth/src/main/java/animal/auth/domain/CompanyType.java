package animal.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {

  PROVIDER(Type.PROVIDER),
  RECEIVE(Type.RECEIVE);

  private final String type;

  public static class Type {

    public static final String PROVIDER = "PROVIDER_COMPANY";
    public static final String RECEIVE = "RECEIVE_COMPANY";

  }
}
