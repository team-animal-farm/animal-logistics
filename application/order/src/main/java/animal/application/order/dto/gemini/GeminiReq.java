package animal.application.order.dto.gemini;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiReq {

    private List<Content> contents;

    public void createGeminiReqDto(String text) {
        this.contents = new ArrayList<>();
        Content content = new Content(text);
        contents.add(content);
    }

    @Data
    public class Content {

        private List<Part> parts;

        public Content(String text) {
            parts = new ArrayList<>();
            Part part = new Part(text);
            parts.add(part);
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public class Part {

            private String text;
        }
    }
}
