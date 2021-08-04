import { VaccinationState } from '../../components';
import { Frame } from '../../components/common';
import { Container, Title, Div } from './StatusPage.styles';

const StatusPage = () => {
  return (
    <Container>
      <Title>접종 현황</Title>
      <VaccinationState />
      <Div>
        <Frame width="100%">
          <div>asd</div>
        </Frame>
        <Frame width="100%">
          <div>asd</div>
        </Frame>
      </Div>
    </Container>
  );
};

export default StatusPage;
