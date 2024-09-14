package animal.domain.manager;

import exception.GlobalException;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import response.ErrorCase;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {

    private static final Pattern pattern = Pattern.compile("^[a-z0-9]{4,10}$");

    private String username;

    public static Username of(String username) {
        if (!pattern.matcher(username).matches()) {
            throw new GlobalException(ErrorCase.INVALID_INPUT);
        }

        Username name = new Username();
        name.username = username;
        return name;
    }

    @Override
    public String toString() {
        return username;
    }
}
