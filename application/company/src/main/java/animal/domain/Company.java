package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @Column(name = "company_id")
    private UUID id;
    private UUID hubId;
    private String companyStatus;
    private String companyType;

    @Embedded
    private Address address;

}
