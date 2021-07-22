import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useSnackbar } from 'notistack';
import {
  Title,
  Container,
  ButtonContainer,
  frameStyles,
  loginButtonStyles,
  goSignupStyles,
  KakaoButton,
  NaverButton,
} from './LoginPage.styles';
import Frame from '../../components/Frame/Frame';
import Button from '../../components/Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../../components/Button/Button.styles';
import {
  ALERT_MESSAGE,
  NAVER_LOGIN_URL,
  KAKAO_LOGIN_URL,
  LOCAL_STORAGE_KEY,
  PATH,
  SNACKBAR_MESSAGE,
} from '../../constants';
import Input from '../../components/Input/Input';
import { useDispatch } from 'react-redux';
import { getMyInfoAsync } from '../../redux/authSlice';
import { requestPostLogin } from '../../requests';
import KakaoIcon from '../../assets/icons/kakao.svg';
import NaverIcon from '../../assets/icons/naver.svg';

const LoginPage = () => {
  const history = useHistory();
  const dispatch = useDispatch();
  const { enqueueSnackbar } = useSnackbar();

  const [nickname, setNickname] = useState('');

  const goSignupPage = () => {
    history.push(`${PATH.SIGNUP}`);
  };

  const goHomePage = () => {
    history.push(`${PATH.HOME}`);
  };

  const login = async () => {
    try {
      const response = await requestPostLogin({ nickname });

      if (!response.ok) {
        throw new Error(response);
      }

      const userData = await response.json();

      localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(userData?.accessToken));

      dispatch(getMyInfoAsync(userData.accessToken));

      enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_LOGIN);
      goHomePage();
    } catch (error) {
      alert(ALERT_MESSAGE.FAIL_TO_LOGIN);
      console.error(error);
    }
  };

  return (
    <Container>
      <Frame width="48rem" showShadow={true} styles={frameStyles}>
        <Title>로그인</Title>
        {/* <Input
          placeholder="닉네임을 입력해주세요"
          width="100%"
          onChange={(event) => setNickname(event.target.value)}
        />
        <Button styles={loginButtonStyles} onClick={login}>
          로그인하기
        </Button> */}
        <NaverButton type="button" href={NAVER_LOGIN_URL}>
          <NaverIcon width="20" />
          네이버로 시작하기
        </NaverButton>
        <KakaoButton type="button" href={KAKAO_LOGIN_URL}>
          <KakaoIcon width="23" />
          카카오로 시작하기
        </KakaoButton>

        {/* <ButtonContainer>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            styles={goSignupStyles}
            onClick={goSignupPage}
          >
            아직 회원이 아니신가요?
          </Button>
        </ButtonContainer> */}
      </Frame>
    </Container>
  );
};

export default LoginPage;
