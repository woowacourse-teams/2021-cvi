# domain-cvi-aws-s3-service
## 모듈 설명
- AWS S3에 이미지를 업로드/삭제하는 기능을 제공해요.
- AWS에 직접 연결해야하기 때문에 설정파일(application-aws-s3.yml)의 설정값이 필요해요.
- 우테코 계정의 S3는 우테코 계정 내의 EC2에서만 접근할 수 있기 때문에, local 환경에서 테스트하기 위해서는 개인 AWS 계정의 S3와 그에 따른 설정을 사용해야 해요.
  (이에 대한 방법은 문서에 정리해 뒀어요.)

## 의존 모듈 
- 없음

## 도식화
![image](https://user-images.githubusercontent.com/48986787/139180220-01800ced-4b38-4ade-9568-cdaa9711abb1.png)
