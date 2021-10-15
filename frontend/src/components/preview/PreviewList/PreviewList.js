import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import PreviewItem from '../PreviewItem/PreviewItem';
import { Container, Error } from './PreviewList.styles';
import { useFetch, useLoading, useMovePage } from '../../../hooks';
import { fetchGetAllReviewList } from '../../../service/fetch';
import { ERROR_MESSAGE, FILTER_TYPE, SORT_TYPE, THEME_COLOR } from '../../../constants';

const PreviewList = ({ reviewType }) => {
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [offset, setOffset] = useState(0);

  const {
    response: reviewList,
    error: reviewError,
    loading,
  } = useFetch([], () => fetchGetAllReviewList(accessToken, offset, [reviewType, SORT_TYPE.DESC]));
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const { goReviewDetailPage } = useMovePage();

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
