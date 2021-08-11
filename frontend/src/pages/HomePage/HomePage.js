import { VaccinationState, Preview, VaccinationSchedule } from '../../components';
import { FILTER_TYPE } from '../../constants';
import { Container } from './HomePage.styles';

const HomePage = () => (
  <Container>
    <VaccinationState title="접종 현황" withViewMore={true} />
    <Preview title="최신 글" reviewType={FILTER_TYPE.CREATED_AT} />
    <Preview title="실시간 인기 글" reviewType={FILTER_TYPE.LIKE_COUNT} />
    <VaccinationSchedule />
  </Container>
);

export default HomePage;
