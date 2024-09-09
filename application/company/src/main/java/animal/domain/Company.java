package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @Column(name = "company_id")
    private UUID id;
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
    public Company(UUID hubId, String name, String companyType, Address address) {
        this.hubId = hubId;
        this.name = name;
        // 초기값 세팅
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
