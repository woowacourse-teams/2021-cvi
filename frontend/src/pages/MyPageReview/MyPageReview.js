import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { ReviewItem } from '../../components';
import { Frame } from '../../components/common';
import { PATH } from '../../constants';
import { useFetch } from '../../hooks';
import { Container, Title, MyReviewList, frameStyle } from './MyPageReview.styles';
import myReviewList from '../../fixtures/reviewList.json';

const MyPageReview = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  // const { response: myReviewList, error: reviewError } = useFetch([], function () {
  //   return requestGetMyReviewList(accessToken);
  // });

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  return (
    <Container>
      <Title>내가 쓴 글</Title>
      <Frame styles={frameStyle}>
        <MyReviewList>
          {myReviewList?.map((myReview) => (
            <ReviewItem
              key={myReview.id}
              review={myReview}
              accessToken={accessToken}
              onClick={() => goReviewDetailPage(myReview.id)}
            />
          ))}
        </MyReviewList>
      </Frame>
    </Container>
  );
};

export default MyPageReview;
