const BASE_URL = 'https://cvi.o-r.kr/api/v1';
const NAVER_LOGIN_URL =
  'https://nid.naver.com/oauth2.0/authorize?client_id=8nbRZEMcC6ZKkwCgUqNr&response_type=code&redirect_uri=http://localhost:8080/auth/naver/callback&state=STATE';
const KAKAO_LOGIN_URL =
  'https://kauth.kakao.com/oauth/authorize?client_id=1a06cf63be2ce0a6ebd8f49cd534e1c9&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code';

export { BASE_URL, NAVER_LOGIN_URL, KAKAO_LOGIN_URL };
