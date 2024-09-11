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
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private Integer amount;

    @Builder
    private Quantity(Integer amount) {
        validateNegative(amount);
        this.amount = amount;
    }

    public Quantity adjust(int quantity) {
        return new Quantity(this.amount + quantity);
    }

    public Quantity multiply(int multiplier) {
        return new Quantity(this.amount * multiplier);
    }

    private void validateNegative(Integer quantity) {
        if (isNegative(quantity)) {
            throw new GlobalException(ErrorCase.INVALID_INPUT);
        }
    }

    private boolean isNegative(Integer quantity) {
        return quantity < 0;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
