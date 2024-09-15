package animal.property;

import animal.domain.Address;
import animal.domain.Coordinate;

public record HubProperty(
    String name,
    Address address,
    Coordinate coordinate
) {

}
