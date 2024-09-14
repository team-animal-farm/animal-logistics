package animal.infrastructure;

import animal.domain.Company;
import animal.domain.CompanyId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import response.CompanyType;

public interface CompanyRepository extends JpaRepository<Company, CompanyId> {

    Page<Company> findAll(Pageable pageable);

    // companyName, companyType null check 후 검색
    @Query("SELECT c FROM Company c WHERE (:companyName IS NULL OR c.name LIKE %:companyName%) AND (:companyType IS NULL OR c.companyType = :companyType)")
    Page<Company> findCompany(String companyName, CompanyType companyType, Pageable pageable);
}
