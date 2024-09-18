package animal.application.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(name = "message", length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @OneToOne
    private Prompt prompt;

    @Builder
    private Notification(String message, Platform platform, Prompt prompt) {
        this.message = message;
        this.platform = platform;
        this.prompt = prompt;
    }
}
