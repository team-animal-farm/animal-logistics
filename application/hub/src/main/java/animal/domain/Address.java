package animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "zipcode", length = 100, nullable = false)
    private String zipcode;

    @Builder
    private Address(String roadAddress, String detailAddress, String zipcode) {
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }
}