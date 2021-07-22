import { Container } from './HomePage.styles';
import VaccinationState from '../../components/VaccinationState/VaccinationState';
import Preview from '../../components/Preview/Preview';

const HomePage = () => (
  <Container>
    <VaccinationState title="접종 현황" />
    <Preview title="접종 후기" />
  </Container>
);

export default HomePage;
