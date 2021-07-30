import { Button } from '../../components/common';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import {
  buttonStyles,
  Container,
  Title,
  Content,
  Image,
  Label,
  Input,
} from './MyPageShotVerification.styles';
import exampleImg from '../../assets/images/vaccination_example.png';

const MyPageShotVerification = () => {
  const onChange = (event) => {
    const formData = new FormData();
    formData.append('file', event.target.files[0]);
  };

  return (
    <Container>
      <Title>접종 인증</Title>
      <Content>
        <Image src={exampleImg} />
        <Label>
          백신 접종을 인증할 수 있는 사진을 올려주세요
          <Input type="file" onChange={onChange} />
        </Label>
      </Content>
      <Button sizeType={BUTTON_SIZE_TYPE.LARGE} styles={buttonStyles}>
        인증하기
      </Button>
    </Container>
  );
};

export default MyPageShotVerification;
