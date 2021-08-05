import { RegionalStateChart, RegionalStateTable, VaccinationState } from '../../components';
import { useFetch } from '../../hooks';
import { requestVaccinationStateList } from '../../requests';
import { Container, Title, Content } from './StatePage.styles';

const StatePage = () => {
  const { response, error, loading } = useFetch([], requestVaccinationStateList);

  const koreanState = response[0];
  const regionalState = response.slice(1);

  return (
    <Container>
      <Title>접종 현황</Title>
      <Content>
        <VaccinationState withWorld={false} />
        <RegionalStateChart vaccinationStateList={regionalState} />
        <RegionalStateTable vaccinationStateList={regionalState} />
      </Content>
    </Container>
  );
};

export default StatePage;
