import PreviewItem from '../PreviewItem/PreviewItem';
import { Container, Error } from './PreviewList.styles';
import { useFetch } from '../../hooks';
import { requestGetAllReviewList } from '../../requests';
import { ERROR_MESSAGE, PATH } from '../../constants';
import { useHistory } from 'react-router-dom';

const PreviewList = () => {
  const history = useHistory();

  const { response: reviewList, error: reviewError } = useFetch([], requestGetAllReviewList);

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  if (reviewError) {
    return <Error>{ERROR_MESSAGE.FAIL_TO_GET_REVIEW_LIST}</Error>;
  }

  return (
    <Container>
      {reviewList.slice(0, 4).map((review) => (
        <li key={review.id}>
          <PreviewItem review={review} onClick={() => goReviewDetailPage(review.id)} />
        </li>
      ))}
    </Container>
  );
};

export default PreviewList;
