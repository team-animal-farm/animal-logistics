package animal.domain;

import animal.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import response.CompanyStatus;
import response.CompanyType;

@Entity
@Getter
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

    @EmbeddedId
    private final CompanyId id = new CompanyId();

    private String username;
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
    private Company(String username, String name, CompanyType companyType, Address address) {
        this.username = username;
        this.name = name;
        this.companyStatus = CompanyStatus.OPENED;
        this.companyType = companyType;
        this.address = address;
    }

    public void updateCompany(String name, CompanyStatus companyStatus, CompanyType companyType, Address address) {
        this.name = name;
        this.companyStatus = companyStatus;
        this.companyType = companyType;
        this.address = address;
    }

}
