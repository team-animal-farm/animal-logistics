package animal.application.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_prompt")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prompt {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(name = "request_content", length = 1000)
    private String requestContent;

    @Column(name = "response_content", length = 3000)
    private String responseContent;

    @Builder
    private Prompt(String requestContent, String responseContent) {
        this.requestContent = requestContent;
        this.responseContent = responseContent;
    }
}
