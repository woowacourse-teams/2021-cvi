import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import Button from '../../components/Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../../components/Button/Button.styles';
import Frame from '../../components/Frame/Frame';
import Input from '../../components/Input/Input';
import Selection from '../../components/Selection/Selection';
import { AGE_RANGE_LIST, PATH } from '../../constants';
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

const SignupPage = () => {
  const history = useHistory();
  const [selectedAgeRange, setSelectedAgeRange] = useState('10대');

  const goLoginPage = () => {
    history.push(`${PATH.LOGIN}`);
  };

  const ageRangeList = AGE_RANGE_LIST;

  return (
    <Container>
      <Frame styles={frameStyles}>
        <Title>회원 가입</Title>
        <SelectionContainer>
          <SelectionTitle>나이대를 선택해주세요</SelectionTitle>
          <Selection
            selectionList={ageRangeList}
            selectedItem={selectedAgeRange}
            setSelectedItem={setSelectedAgeRange}
          />
        </SelectionContainer>
        <Input placeholder="닉네임을 입력해주세요" width="100%" />
        <Button styles={signupButtonStyles}>회원가입하기</Button>
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
