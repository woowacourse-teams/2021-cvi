import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import { Button, Frame, Input, Selection } from '../../components/common';
import { BUTTON_BACKGROUND_TYPE } from '../../components/common/Button/Button.styles';
import { AGE_RANGE, ALERT_MESSAGE, PATH, RESPONSE_STATE, SNACKBAR_MESSAGE } from '../../constants';
import { getMyInfoAsync } from '../../redux/authSlice';
import { postSignupAsync } from '../../service';
import {
  signupButtonStyles,
  Container,
  Title,
  SelectionTitle,
  SelectionContainer,
  frameStyles,
  goLoginStyles,
  LoginContainer,
  Span,
} from './SignupPage.styles';
import { useSnackBar } from '../../hooks';

const SignupPage = () => {
  const history = useHistory();
  const location = useLocation();
  const dispatch = useDispatch();

  const [selectedAgeRange, setSelectedAgeRange] = useState('10대');
  const [nickname, setNickname] = useState();

  const { isSnackBarOpen, openSnackBar, SnackBar } = useSnackBar();

  const goHomePage = () => {
    history.push(`${PATH.HOME}`);
  };

  const signup = async () => {
    const data = { nickname, ageRange: AGE_RANGE[selectedAgeRange], ...location.state };
    const response = await postSignupAsync(data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    dispatch(getMyInfoAsync(response.data.accessToken));

    openSnackBar();
    goHomePage();
  };

  return (
    <>
      <Container>
        <Frame width="60rem" widthshowShadow={true} styles={frameStyles}>
          <Title>회원 가입</Title>
          <SelectionContainer>
            <SelectionTitle>나이대를 선택해주세요</SelectionTitle>
            <Selection
              selectionList={Object.keys(AGE_RANGE)}
              selectedItem={selectedAgeRange}
              setSelectedItem={setSelectedAgeRange}
            />
          </SelectionContainer>
          <Input
            placeholder="닉네임을 입력해주세요"
            width="100%"
            value={nickname}
            onChange={(event) => setNickname(event.target.value)}
          />
          <Button styles={signupButtonStyles} onClick={signup}>
            회원가입하기
          </Button>
          <LoginContainer>
            <Span>이미 회원이신가요?</Span>
            <Button
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              styles={goLoginStyles}
              onClick={goHomePage}
            >
              로그인 하기
            </Button>
          </LoginContainer>
        </Frame>
      </Container>
      {isSnackBarOpen && <SnackBar>{SNACKBAR_MESSAGE.SUCCESS_TO_SIGNUP}</SnackBar>}
    </>
  );
};

export default SignupPage;
