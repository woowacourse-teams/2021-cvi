const BASE_URL = process.env.REACT_APP_SERVER_URL;
const NAVER_LOGIN_URL = `https://nid.naver.com/oauth2.0/authorize?client_id=nr6cVo7X8bw1cRQCKOQu&response_type=code&redirect_uri=${process.env.REACT_APP_SOCIAL_LOGIN_CALLBACK}/auth/naver/callback&state=f6c6f67696e2e746d6f6e2e636f2e6b725c2f757365725c2f736f6369616c63616c6c6261636b222c227265717565737450617468223a225c2f757365725c2f7369676e7570222c2272657475a6c7s87d7fe7a67va7aaa6sd7e76b7e6r6g6h6ja76z7s54z54z3s79d`;
const KAKAO_LOGIN_URL = `https://kauth.kakao.com/oauth/authorize?client_id=1a06cf63be2ce0a6ebd8f49cd534e1c9&redirect_uri=${process.env.REACT_APP_SOCIAL_LOGIN_CALLBACK}/auth/kakao/callback&response_type=code`;
const VACCINATION_RESERVATION_URL = 'https://ncvr.kdca.go.kr/';
const NAVER_LEFT_VACCINATION_URL = 'https://v-search.nid.naver.com/home';

export {
  BASE_URL,
  NAVER_LOGIN_URL,
  KAKAO_LOGIN_URL,
  VACCINATION_RESERVATION_URL,
  NAVER_LEFT_VACCINATION_URL,
};
