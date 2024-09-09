# 디렉토리 가이드

- 애플리케이션 레벨에서 사용하는 디렉토리 가이드 입니다.
- 디렉토리명은 소문자로 작성하며, 단어 사이는 `-`로 구분합니다.
- 디렉토리명은 단수형으로 작성합니다.

```text
- sample : 샘플 애플리케이션 
      - presentation: 프레젠테이션 계층
          - SampleController.class: 컨트롤러
      - application: 응용 계층
          - SampleService.class: 응용 서비스
          (or) - SampleCreateService.class: 생성 응용 서비스 
      - domain: 도메인 계층
          - Sample.class: 엔티티
          - SampleValue.class: 값 객체
          - Sample<행위>Service.class: 도메인 서비스
      - infrastructure: 인프라스트럭처 계층
          - SampleRepository.class: 리포지토리
      - dto: 데이터 전송 객체
          - SampleRequest.class: 요청 객체
          - SampleResponse.class: 응답 객체
      - mapper: 매퍼
        - SampleMapper.class: 매퍼
      - property: 프로퍼티
        - SampleProperty.class: 프로퍼티
```

## 응용 서비스 vs 도메인 서비스

### 응용 서비스

- 트랜잭션 처리 및 도메인 객체 간 흐름 제어

### 도메인 서비스

- 여러 애그리거트를 필요로 하는 로직을 처리하거나, 하나의 애그리거트에서 처리하기 복잡한 로직을 담당

## 리포지토리

- Repository는 도메인 디렉토리에 위치하는 것이 맞으나, JPA를 사용할 경우 JPA Repository를 사용하게 되면 인프라스트럭처 계층에 위치하도록 한다.
