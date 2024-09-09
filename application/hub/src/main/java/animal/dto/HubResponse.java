package animal.dto;

import animal.domain.Address;
import animal.domain.Coordinate;
import java.util.UUID;

public class HubResponse {

    public record GetHubRes(
        UUID id,
        Address address,
        Coordinate coordinate
    ) {

    }

    public record CreateHubRes(
        UUID id,
        Address address,
        Coordinate coordinate
    ) {

    }
}
