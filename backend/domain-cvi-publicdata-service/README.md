# domain-cvi-publicdata-service
## 모듈 설명
- 공공데이터와 관련된 조회와 저장의 비즈니스 흐름을 담당해요.
- 조회에 대하여 캐싱을 사용했어요. 

## 의존 모듈
- `domain-cvi`: Model에 CR 작업을 위임해요.
- `cvi-publicdata-parser`: PublicData Api 요청을 위임해요.

## 외부 제공 모듈 
- `domain-cvi`

## 도식화 
![image](https://user-images.githubusercontent.com/48986787/139180207-e1f62aae-0de7-4c2f-a243-3eda83bf3882.png)
