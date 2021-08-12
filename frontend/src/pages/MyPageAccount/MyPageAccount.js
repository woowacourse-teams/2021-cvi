import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Button, Input, Selection } from '../../components/common';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import {
  AGE_RANGE,
  ALERT_MESSAGE,
  NICKNAME_LIMIT,
  REGEX,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
} from '../../constants';
import { useSnackBar } from '../../hooks';
import { getMyInfoAsync } from '../../redux/authSlice';
import { putAccountAsync } from '../../service';
import {
  Container,
  Title,
  ProfileImage,
  InfoContainer,
  Info,
  buttonStyles,
  inputStyles,
  disabledStyles,
  AgeRange,
} from './MyPageAccount.styles';

const MyPageAccount = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [updatedNickname, setUpdatedNickname] = useState(user?.nickname);
  const [updatedAgeRange, setUpdatedAgeRange] = useState(user?.ageRange?.meaning ?? '10대');

  const { openSnackBar } = useSnackBar();

  const isValidNickname = (nickname) =>
    nickname.length >= NICKNAME_LIMIT.MIN_LENGTH &&
    nickname.length <= NICKNAME_LIMIT.MAX_LENGTH &&
    !REGEX.INCLUDE_BLANK.test(nickname) &&
    !REGEX.INCLUDE_SPECIAL_CHARACTER.test(nickname);

  const editAccount = async (event) => {
    event.preventDefault();

    if (!isValidNickname(updatedNickname)) {
      alert(ALERT_MESSAGE.FAIL_TO_LOGIN);

      return;
    }

    const { socialId, socialProvider, socialProfileUrl, shotVerified } = user;

    const data = {
      nickname: updatedNickname,
      ageRange: AGE_RANGE[updatedAgeRange],
      socialId,
      socialProfileUrl,
      shotVerified,
      socialProvider,
    };
    const response = await putAccountAsync(accessToken, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT_ACCOUNT);

      return;
    }

    dispatch(getMyInfoAsync(accessToken));

    openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_ACCOUNT);
  };

  const isDisableButton =
    updatedNickname === user.nickname && updatedAgeRange === user.ageRange?.meaning;

  useEffect(() => {
    setUpdatedNickname(user.nickname);
    setUpdatedAgeRange(user.ageRange?.meaning);
  }, [user.nickname, user.ageRange?.meaning]);

  return (
    <Container>
      <Title>내 정보 관리</Title>
      <form onSubmit={(event) => editAccount(event)}>
        <InfoContainer>
          <ProfileImage src={user.socialProfileUrl} />
          <Info>
            <Input
              width="51.5rem"
              labelText="닉네임"
              value={updatedNickname}
              labelStyles={inputStyles}
              inputStyles={inputStyles}
              onChange={(event) => setUpdatedNickname(event.target.value)}
            />
            <AgeRange>나이대</AgeRange>
            {updatedAgeRange && (
              <Selection
                selectionList={Object.keys(AGE_RANGE)}
                selectedItem={updatedAgeRange}
                setSelectedItem={setUpdatedAgeRange}
              />
            )}
          </Info>
        </InfoContainer>
        <Button
          type="submit"
          sizeType={BUTTON_SIZE_TYPE.LARGE}
          styles={isDisableButton ? disabledStyles : buttonStyles}
          disabled={isDisableButton}
        >
          수정하기
        </Button>
      </form>
    </Container>
  );
};

export default MyPageAccount;
