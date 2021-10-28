# 소나큐브 정적 리포트
![image](https://user-images.githubusercontent.com/48986787/139186557-14ebc92c-f54c-483c-ab72-f20fb8b5a96d.png)


# 멀티모듈 프로젝트 구조 설명 
![image](https://user-images.githubusercontent.com/48986787/139174604-f0ff822f-0087-4d2a-b663-cfe9585171ae.png)
## 모듈별 설명 
### app-cvi-api
```java
- 사용자가 사용할 수 있는 기능을 제공하는 `애플리케이션 모듈`이에요.
- Controller, View의 책임을 가지고 있어요. 
- 의존하는 모듈의 비즈니스 흐름 제어를 담당해요.
```

### common-cvi
```java
- 공통적으로 사용하는 예외 객체를 제공하는 `공통 모듈`이에요
- 커스텀 예외, Util성 클래스가 이 모듈에 속해요.
```

### cvi-publicdate-parser
```java
- 백신 현황 공공데이터를 제공하는 API 서비스에, Http요청을 보내, 응답 받은 Json값을 매핑하는 모듈이에요.
- 스프링 의존성이 아닌, 자바에서 제공하는 URL Connection을 이용해요. 
```

### domain-cvi
```java
- 비즈니스 로직을 수행하는 `도메인 모듈`이에요.
- 하나의 인프라스트럭쳐(DBMS)을 가지며 CRUD를 수행해요.
- Model의 책임을 지니고 있어요.
```

### domain-cvi-aws-s3-service
```java
- AWS S3에 이미지를 업로드/삭제하는 기능을 제공해요.
- AWS에 직접 연결해야하기 때문에 설정파일(application-aws-s3.yml)의 설정값이 필요해요.
- 우테코 계정의 S3는 우테코 계정 내의 EC2에서만 접근할 수 있기 때문에, local 환경에서 테스트하기 위해서는 개인 AWS 계정의 S3와 그에 따른 설정을 사용해야 해요.
  (이에 대한 방법은 문서에 정리해 뒀어요.)
```

### domain-cvi-oauth-service
```java
- 소셜 로그인 서비스에 사용자 데이터를 요청하는 모듈이에요.
```

### domain-cvi-publicdata-service
```java
- 공공데이터와 관련된 조회와 저장의 비즈니스 흐름을 담당해요.
- 조회에 대하여 캐싱을 사용했어요.
```

### domain-cvi-scheduler
```java
- 정해진 시간 마다, 로직을 실행하는 모듈이에요. 
```