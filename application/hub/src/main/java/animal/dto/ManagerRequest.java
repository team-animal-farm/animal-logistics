package animal.dto;

import java.util.UUID;

public class ManagerRequest {

    public record AddHubManagerReq(
        String username
    ) {

    }

    public record AddHubDeliveryManagerReq(
        String username,
        String slackId
    ) {

    }

    public record AddCompanyDeliveryManagerReq(
        String username,
        String slackId
    ) {

    }

    public record AddProviderCompanyManagerReq(
        String username,
        String slackId,
        UUID companyId
    ) {

    }
}
