package animal.infrastructure;

import animal.domain.Product;
import animal.domain.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {

}
