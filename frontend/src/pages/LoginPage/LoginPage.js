import { useHistory } from 'react-router-dom';
import {
  Title,
  Container,
  ButtonContainer,
  frameStyles,
  loginButtonStyles,
  signupButtonStyles,
} from './LoginPage.styles';
import Frame from '../../components/Frame/Frame';
import Button from '../../components/Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../../components/Button/Button.styles';
import { PATH } from '../../constants';
import Input from '../../components/Input/Input';

const LoginPage = () => {
  const history = useHistory();

  const goSignupPage = () => {
    history.push(`${PATH.SIGNUP}`);
  };

  return (
    <Container>
      <Frame width="48rem" styles={frameStyles}>
        <Title>로그인</Title>
        <Input placeholder="닉네임을 입력해주세요." width="100%" />
        <Button styles={loginButtonStyles}>로그인하기</Button>
        <ButtonContainer>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            styles={signupButtonStyles}
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
