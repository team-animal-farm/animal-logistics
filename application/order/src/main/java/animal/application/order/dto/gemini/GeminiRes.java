package animal.application.order.dto.gemini;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRes {

    private List<Candidate> candidates;

    public record Candidate(
        Content content,
        String finishReason
    ) {

    }

    public record Content(
        List<Parts> parts,
        String role
    ) {

    }

    public record Parts(
        String text
    ) {

    }
}