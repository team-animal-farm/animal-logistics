package animal.auth.infrastructure;

import animal.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  boolean existsByEmail(String email);

  User findByEmail(String email);
}
