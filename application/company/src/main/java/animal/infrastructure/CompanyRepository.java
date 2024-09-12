package animal.infrastructure;

import animal.domain.Company;
import animal.domain.CompanyId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, CompanyId> {

    Page<Company> findAll(Pageable pageable);

}
