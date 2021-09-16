import { VaccinationState, Preview, HomeContent } from '../../components';
import { FILTER_TYPE } from '../../constants';
import { Container } from './HomePage.styles';

const HomePage = () => (
  <Container>
    <VaccinationState title="접종 현황" withViewMore={true} withSource={true} />
    <Preview title="최신 글" reviewType={FILTER_TYPE.CREATED_AT} />
    <Preview title="실시간 인기 글" reviewType={FILTER_TYPE.LIKE_COUNT} />
    <HomeContent title="접종 증상" withSource={true} />
  </Container>
);

export default HomePage;
