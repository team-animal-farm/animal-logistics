package animal.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String roadAddress;
    private String detailAddress;
    private String zipcode;

    @Builder
    public Address(String roadAddress, String detailAddress, String zipcode) {
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }

}