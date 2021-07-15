import PreviewItem from '../PreviewItem/PreviewItem';
import { Container, Error } from './PreviewList.styles';
import { useFetch } from '../../hooks';
import { requestGetReviewList } from '../../requests';
import { ERROR_MESSAGE } from '../../constants';

const PreviewList = () => {
  const { response: reviewList, error: reviewError } = useFetch([], requestGetReviewList);

  if (reviewError) {
    return <Error>{ERROR_MESSAGE.FAIL_TO_GET_REVIEW_LIST}</Error>;
  }

  return (
    <Container>
      {reviewList.slice(0, 4).map((review) => (
        <li key={review.id}>
          <PreviewItem review={review} />
        </li>
      ))}
    </Container>
  );
};

export default PreviewList;
