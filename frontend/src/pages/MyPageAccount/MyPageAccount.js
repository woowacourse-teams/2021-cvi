import { useSnackbar } from 'notistack';
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Button, Input, Selection } from '../../components/common';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import { AGE_RANGE, ALERT_MESSAGE, RESPONSE_STATE, SNACKBAR_MESSAGE } from '../../constants';
import { getMyInfoAsync } from '../../redux/authSlice';
import { putAccountAsync } from '../../service';
import {
  Container,
  Title,
  ProfileImage,
  InfoContainer,
  Info,
  buttonStyles,
  AgeRange,
} from './MyPageAccount.styles';

const MyPageAccount = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);
  const { enqueueSnackbar } = useSnackbar();
  const [nickname, setNickname] = useState('');
  const [ageRange, setAgeRange] = useState('');

  const editAccount = async (event) => {
    event.preventDefault();

    const data = {
      nickname,
      ageRange: AGE_RANGE[ageRange],
      socialProfileUrl: user.socialProfileUrl,
    };
    const response = await putAccountAsync(accessToken, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT_ACCOUNT);
      location.reload();

      return;
    }

    dispatch(getMyInfoAsync(accessToken));

    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_ACCOUNT);
  };

  useEffect(() => {
    setNickname(user.nickname);
    setAgeRange(user.ageRange?.meaning);
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
              value={nickname}
              onChange={(event) => setNickname(event.target.value)}
            />
            <AgeRange>나이대</AgeRange>
            {ageRange && (
              <Selection
                selectionList={Object.keys(AGE_RANGE)}
                selectedItem={ageRange}
                setSelectedItem={setAgeRange}
              />
            )}
          </Info>
        </InfoContainer>
        <Button type="submit" sizeType={BUTTON_SIZE_TYPE.LARGE} styles={buttonStyles}>
          수정하기
        </Button>
      </form>
    </Container>
  );
};

export default MyPageAccount;
