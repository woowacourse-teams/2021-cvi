import PreviewItem from '../PreviewItem/PreviewItem';
import { Container, Error } from './PreviewList.styles';
import { useFetch, useLoading } from '../../hooks';
import { requestGetAllReviewList } from '../../requests';
import { ERROR_MESSAGE, PATH, THEME_COLOR } from '../../constants';
import { useHistory } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

const PreviewList = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [offset, setOffset] = useState(0);

  const {
    response: reviewList,
    error: reviewError,
    loading,
  } = useFetch([], () => requestGetAllReviewList(accessToken, offset));
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  useEffect(() => {
    loading ? showLoading() : hideLoading();
  }, [loading]);

  if (reviewError) {
    return <Error>{ERROR_MESSAGE.FAIL_TO_GET_REVIEW_LIST}</Error>;
  }

  return (
    <Container>
      {isLoading ? (
        <Loading isLoading={isLoading} backgroundColor={THEME_COLOR.WHITE} />
      ) : (
        reviewList.slice(0, 6).map((review) => (
          <li key={review.id}>
            <PreviewItem review={review} onClick={() => goReviewDetailPage(review.id)} />
          </li>
        ))
      )}
    </Container>
  );
};

export default PreviewList;
