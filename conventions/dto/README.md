# DTO 가이드

- DTO는 요청, 응답 각 1개의 Class로만 구성한다.
- DTO 내부 클래스로 Record 타입으로 생성합니다.
- DTO 내부 클래스의 네이밍은 다음과 같습니다.
    - `<행위><대상><Req|res>`
        - 행위: Get, Create, Add, Update, Edit, Delete
        - 대상: User, Order, Comment, ...
        - ex) CreateUserReq, GetUserRes

```java

public class SampleRequest {
    
    public record GetSampleRequestDto(
        
        @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,20}$", message = "이름은 한글, 영문, 숫자만 20자 이내로 입력 가능합니다.")
        String name,

        @NotNull(message = "금액은 필수값입니다.")
        Long money
    ) {
        
    }
}
```
