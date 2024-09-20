package animal.infrastructure;

import animal.domain.Hub;
import animal.domain.HubId;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HubRepository extends JpaRepository<Hub, HubId> {


    //companyId
    @Query("SELECT h FROM Hub h JOIN h.providerCompanyManagerList pcm WHERE pcm.companyId = :companyId")
    Optional<Hub> findByCompanyId(@Param("companyId") UUID companyId);

}
