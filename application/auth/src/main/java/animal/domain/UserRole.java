package animal.domain;

import lombok.Getter;

@Getter
public enum UserRole {

    COMPANY(Authority.COMPANY),
    DELIVER(Authority.DELIVERY),
    MANAGER(Authority.MANAGER),
    MASTER(Authority.MASTER);


    private final String authority;
    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority{
        public static final String COMPANY="ROLE_COMPANY";
        public static final String DELIVERY="ROLE_DELIVERY";
        public static final String MANAGER="ROLE_MANAGER";
        public static final String MASTER = "ROLE_MASTER";
    }
}
