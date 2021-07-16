import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  Title,
  Container,
  ButtonContainer,
  frameStyles,
  loginButtonStyles,
  goSignupStyles,
} from './LoginPage.styles';
import Frame from '../../components/Frame/Frame';
import Button from '../../components/Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../../components/Button/Button.styles';
import { PATH } from '../../constants';
import Input from '../../components/Input/Input';
import { useDispatch } from 'react-redux';
import { loginAsync } from '../../redux/authSlice';

const LoginPage = () => {
  const history = useHistory();
  const dispatch = useDispatch();

  const [nickname, setNickname] = useState('');

  const goSignupPage = () => {
    history.push(`${PATH.SIGNUP}`);
  };

  const goHomePage = () => {
    history.push(`${PATH.HOME}`);
  };

  const login = () => {
    dispatch(loginAsync({ nickname }));
    // 로그인 실패시 redirect 안 해야함
    goHomePage();
  };

  return (
    <Container>
      <Frame width="48rem" styles={frameStyles}>
        <Title>로그인</Title>
        <Input
          placeholder="닉네임을 입력해주세요"
          width="100%"
          onChange={(event) => setNickname(event.target.value)}
        />
        <Button styles={loginButtonStyles} onClick={login}>
          로그인하기
        </Button>
        <ButtonContainer>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            styles={goSignupStyles}
            onClick={goSignupPage}
          >
            아직 회원이 아니신가요?
          </Button>
        </ButtonContainer>
      </Frame>
    </Container>
  );
};

export default LoginPage;
