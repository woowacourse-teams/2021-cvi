# app-cvi-api
## 모듈 설명
- 사용자가 사용할 수 있는 기능을 제공하는 `애플리케이션 모듈`이에요.
- Controller, View의 책임을 가지고 있어요. 
- 의존하는 모듈의 비즈니스 흐름 제어를 담당해요.

## 의존 모듈 
- `domain-cvi`: Model에 CRUD 작업을 위임해요.
- `domain-cvi-oauth-service`: 소셜 로그인 요청에 대한 서비스를 위임해요. 
- `domain-cvi-publicdata-service`: 공공 데이터 요청에 대한 서비스를 위임해요.
- `domain-cvi-scheduler`: 일정 기간마다 수행되어야 하는 작업을 위임해요.
- `domain-cvi-aws-s3-service`: 게시글의 사진을 저장하는 작업을 위임해요.

## 도식화
![image](https://user-images.githubusercontent.com/48986787/139180135-dd2a12dd-23b1-487c-9ac6-5375e698ff6d.png)
