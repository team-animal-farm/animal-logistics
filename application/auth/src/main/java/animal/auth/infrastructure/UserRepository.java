package animal.auth.infrastructure;

import animal.auth.domain.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  boolean existsByEmail(String email);

  Page<User> findAllByHubId(UUID hubId, Pageable pageable);

}
