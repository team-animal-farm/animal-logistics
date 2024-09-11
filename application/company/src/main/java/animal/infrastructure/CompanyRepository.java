package animal.infrastructure;

import animal.domain.Company;
import animal.domain.CompanyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, CompanyId> {

}
