package animal.domain;

import exception.GlobalException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import response.ErrorCase;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {

    private static final double MAX_LATITUDE = 90.0;
    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LONGITUDE = 180.0;
    private static final double MIN_LONGITUDE = -180.0;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Builder
    private Coordinate(Double latitude, Double longitude) {
        if (!isValidLatitude(latitude) || !isValidLongitude(longitude)) {
            throw new GlobalException(ErrorCase.INVALID_INPUT);
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    private boolean isValidLatitude(Double latitude) {
        return MIN_LATITUDE <= latitude && latitude <= MAX_LATITUDE;
    }

    private boolean isValidLongitude(Double longitude) {
        return MIN_LONGITUDE <= longitude && longitude <= MAX_LONGITUDE;
    }
}