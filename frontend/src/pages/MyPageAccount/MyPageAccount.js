import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Button, Input, Selection } from '../../components/common';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import { AGE_RANGE } from '../../constants';
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
  const user = useSelector((state) => state.authReducer.user);

  const [ageRange, setAgeRange] = useState('');

  useEffect(() => {
    setAgeRange(user.ageRange?.meaning);
  }, [user.ageRange?.meaning]);

  return (
    <Container>
      <Title>내 정보 관리</Title>
      <InfoContainer>
        <ProfileImage src={user.socialProfileUrl} />
        <Info>
          <Input width="51.5rem" labelText="닉네임" value={user.nickname} />
          <AgeRange>나이대</AgeRange>
          <Selection
            selectionList={Object.keys(AGE_RANGE)}
            selectedItem={ageRange}
            setSelectedItem={setAgeRange}
          />
        </Info>
      </InfoContainer>
      <Button sizeType={BUTTON_SIZE_TYPE.LARGE} styles={buttonStyles}>
        수정하기
      </Button>
    </Container>
  );
};

export default MyPageAccount;
