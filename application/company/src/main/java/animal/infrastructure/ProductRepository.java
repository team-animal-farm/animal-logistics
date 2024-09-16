package animal.infrastructure;

import animal.domain.Company;
import animal.domain.Product;
import animal.domain.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {

    Page<Product> findByCompanyAndNameContaining(Company company, String name, Pageable pageable);
}
