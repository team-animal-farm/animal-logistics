package animal.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Address {

    private String roadAddress;
    private String detailAddress;
    private String zipcode;

    @Builder
    private Address(String roadAddress, String detailAddress, String zipcode) {
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }

}