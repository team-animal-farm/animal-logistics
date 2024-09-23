package animal.application.hub.property;

import animal.application.hub.domain.hub.Address;
import animal.application.hub.domain.hub.Coordinate;

public record HubProperty(
    String name,
    Address address,
    Coordinate coordinate
) {

}
