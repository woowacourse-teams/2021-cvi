import { RegionalStateChart, RegionalStateTable, VaccinationState } from '../../components';
import { Container, Title, RegionalContainer } from './StatePage.styles';

const StatePage = () => {
  return (
    <Container>
      <Title>접종 현황</Title>
      <VaccinationState />
      <RegionalContainer>
        <RegionalStateChart />
        <RegionalStateTable />
      </RegionalContainer>
    </Container>
  );
};

export default StatePage;
