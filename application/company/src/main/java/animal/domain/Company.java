package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;
import jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

    @EmbeddedId
    private final CompanyId id = new CompanyId();

    private UUID hubId;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CompanyStatus companyStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CompanyType companyType;

    @Embedded
    private Address address;

    @Builder
    private Company(UUID hubId, String name, String companyType, Address address) {
        this.hubId = hubId;
        this.name = name;
        this.companyStatus = CompanyStatus.OPENED;
        this.companyType = CompanyType.valueOf(companyType);
        this.address = address;
    }

    public enum CompanyStatus {
        OPENED, CLOSED
    }

    public enum CompanyType {
        PRODUCER, RECEIVER
    }

}
