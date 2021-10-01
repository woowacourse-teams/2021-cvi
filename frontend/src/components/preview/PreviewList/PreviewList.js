import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import PreviewItem from '../PreviewItem/PreviewItem';
import { Container, Error } from './PreviewList.styles';
import { useFetch, useLoading } from '../../../hooks';
import { requestGetAllReviewList } from '../../../requests';
import { ERROR_MESSAGE, FILTER_TYPE, PATH, SORT_TYPE, THEME_COLOR } from '../../../constants';

const PreviewList = ({ reviewType }) => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [offset, setOffset] = useState(0);

  const {
    response: reviewList,
    error: reviewError,
    loading,
  } = useFetch([], () =>
    requestGetAllReviewList(accessToken, offset, [reviewType, SORT_TYPE.DESC]),
  );
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
        reviewList.slice(0, 4).map((review) => (
          <li key={review.id}>
            <PreviewItem
              review={review}
              accessToken={accessToken}
              onClick={() => goReviewDetailPage(review.id)}
            />
          </li>
        ))
      )}
    </Container>
  );
};

PreviewList.propTypes = {
  reviewType: PropTypes.string,
};

PreviewList.defaultProps = {
  reviewType: FILTER_TYPE.CREATED_AT,
};

export default PreviewList;
