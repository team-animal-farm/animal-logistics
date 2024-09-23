package animal.application.hub.domain.inventory;

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
public class Money {

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Builder
    private Money(Long amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        return new Money(this.amount + money.amount);
    }

    public Money subtract(final Money money) {
        return new Money(this.amount - money.amount);
    }

    public Money multiply(int multiplier) {
        return new Money(this.amount * multiplier);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
