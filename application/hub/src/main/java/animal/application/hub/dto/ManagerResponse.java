package animal.application.hub.dto;

import java.util.UUID;

public class ManagerResponse {

    public record GetHubManagerRes(
        String username
    ) {

    }

    public record GetHubDeliveryManagerRes(
        String username,
        String slackId
    ) {

    }

    public record GetCompanyDeliveryManagerRes(
        String username,
        String slackId
    ) {

    }

    public record GetProviderCompanyManagerRes(
        String username,
        String slackId,
        UUID companyId
    ) {

    }
}
