import { Title, Container, frameStyles, KakaoButton, NaverButton } from './LoginPage.styles';
import Frame from '../../components/Frame/Frame';
import { NAVER_LOGIN_URL, KAKAO_LOGIN_URL } from '../../constants';
import { KakaoIcon, NaverIcon } from '../../assets/icons';

const LoginPage = () => (
  <Container>
    <Frame width="48rem" showShadow={true} styles={frameStyles}>
      <Title>로그인</Title>
      <NaverButton type="button" href={NAVER_LOGIN_URL}>
        <NaverIcon width="20" />
        네이버로 시작하기
      </NaverButton>
      <KakaoButton type="button" href={KAKAO_LOGIN_URL}>
        <KakaoIcon width="23" />
        카카오로 시작하기
      </KakaoButton>
    </Frame>
  </Container>
);

export default LoginPage;
