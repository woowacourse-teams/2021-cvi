import { VaccinationState, Preview } from '../../components';
import { FILTER_TYPE } from '../../constants';
import { Container } from './HomePage.styles';

const HomePage = () => (
  <Container>
    <VaccinationState title="접종 현황" />
    <Preview title="최신 글" reviewType={FILTER_TYPE.CREATED_AT} />
    <Preview title="좋아요 많은 글" reviewType={FILTER_TYPE.LIKE_COUNT} />
    <Preview title="조회수 높은 글" reviewType={FILTER_TYPE.VIEW_COUNT} />
  </Container>
);

export default HomePage;
