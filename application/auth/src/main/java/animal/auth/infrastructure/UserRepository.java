package animal.auth.infrastructure;

import animal.auth.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);
}
