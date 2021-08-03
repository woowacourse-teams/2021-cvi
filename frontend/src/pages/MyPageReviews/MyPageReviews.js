import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { ReviewItem } from '../../components';
import { Frame } from '../../components/common';
import { PATH } from '../../constants';
import { useFetch } from '../../hooks';
import { requestGetAllReviewList } from '../../requests';
import { Container, Title, MyReviewList, MyReview } from './MyPageReviews.styles';

const MyPageReviews = () => {
  const history = useHistory();

  const { response: myReviewList } = useFetch([], requestGetAllReviewList);

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  return (
    <Container>
      <div>MyPageReviews</div>
      <Title>내가 쓴 글</Title>
      <Frame>
        <MyReviewList>
          {myReviewList?.map((myReview) => (
            // 1안
            <ReviewItem
              key={myReview.id}
              review={myReview}
              onClick={() => goReviewDetailPage(myReview.id)}
            />
            // 2안
            // <MyReview key={myReview.id}>
            //   <div>{myReview.content}</div>
            // </MyReview>
          ))}
        </MyReviewList>
      </Frame>
    </Container>
  );
};

export default MyPageReviews;
