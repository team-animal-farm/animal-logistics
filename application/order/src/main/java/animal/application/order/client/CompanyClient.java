package animal.application.order.client;


import animal.application.order.domain.delivery.Address;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "company-service",
    path = "/companies"
)
public interface CompanyClient {

    @GetMapping("/address/{receiveCompanyId}")
    Address getAddress(@PathVariable UUID receiveCompanyId);

    @GetMapping("/recipient/{receiveCompanyId}")
    String getRecipient(@PathVariable UUID receiveCompanyId);
}

