import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation } from 'react-router-dom';
import { Button, Frame, Input, Selection } from '../../components/@common';
import { BUTTON_BACKGROUND_TYPE } from '../../components/@common/Button/Button.styles';
import {
  AGE_RANGE,
  ALERT_MESSAGE,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
  SERVER_MESSAGE,
  NICKNAME_LIMIT,
  REGEX,
  LOCAL_STORAGE_KEY,
} from '../../constants';
import { getMyInfoAsync } from '../../redux/authSlice';
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
import { useMovePage, useSnackbar } from '../../hooks';
import { fetchPostSignup } from '../../service/fetch';
import customRequest from '../../service/customRequest';

const SignupPage = () => {
  const location = useLocation();
  const dispatch = useDispatch();

  const [selectedAgeRange, setSelectedAgeRange] = useState('10대');
  const [nickname, setNickname] = useState();

  const { openSnackbar } = useSnackbar();
  const { goHomePage, goLoginPage } = useMovePage();

  const isValidNickname = (nickname) =>
    nickname.length >= NICKNAME_LIMIT.MIN_LENGTH &&
    nickname.length <= NICKNAME_LIMIT.MAX_LENGTH &&
    !REGEX.INCLUDE_BLANK.test(nickname) &&
    !REGEX.INCLUDE_SPECIAL_CHARACTER.test(nickname);

  const signup = async (event) => {
    event.preventDefault();

    if (!isValidNickname(nickname)) {
      alert(ALERT_MESSAGE.FAIL_TO_LOGIN);

      return;
    }

    const data = { nickname, ageRange: AGE_RANGE[selectedAgeRange], ...location.state };
    const response = await customRequest(() => fetchPostSignup(data));

    if (response.state === RESPONSE_STATE.FAILURE) {
      if (response.data.includes(SERVER_MESSAGE.DUPLICATED_NICJNAME)) {
        alert(ALERT_MESSAGE.FAIL_TO_SIGN_DUPLICATED_NICKNAME);

        return;
      }

      alert(ALERT_MESSAGE.FAIL_TO_SIGNUP);

      return;
    }

    localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(response.data.accessToken));
    dispatch(getMyInfoAsync(response.data.accessToken));

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_SIGNUP);
    goHomePage();
  };

  return (
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
        <form onSubmit={signup}>
          <Input
            placeholder="닉네임을 입력해주세요"
            width="100%"
            value={nickname}
            minLength={NICKNAME_LIMIT.MIN_LENGTH}
            maxLength={NICKNAME_LIMIT.MAX_LENGTH}
            required={true}
            onChange={(event) => setNickname(event.target.value)}
          />
          <Button styles={signupButtonStyles}>회원가입하기</Button>
        </form>
        <LoginContainer>
          <Span>이미 회원이신가요?</Span>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            styles={goLoginStyles}
            onClick={goLoginPage}
          >
            로그인 하기
          </Button>
        </LoginContainer>
      </Frame>
    </Container>
  );
};

export default SignupPage;
