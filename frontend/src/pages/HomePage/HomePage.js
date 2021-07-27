import { Preview, VaccinationState } from '../../components';
import { Container } from './HomePage.styles';

const HomePage = () => (
  <Container>
    <VaccinationState title="접종 현황" />
    <Preview title="접종 후기" />
  </Container>
);

export default HomePage;
