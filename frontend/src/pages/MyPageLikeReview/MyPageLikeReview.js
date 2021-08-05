import { useHistory } from 'react-router-dom';
import { ReviewItem } from '../../components';
import { Frame } from '../../components/common';
import { PATH } from '../../constants';
import { useFetch } from '../../hooks';
import { Container, Title, MyLikeReviewList, frameStyle } from './MyPageLikeReview.styles';
import myLikeReviewList from '../../fixtures/reviewList.json';
import { useSelector } from 'react-redux';

const MyPageLikeReview = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  // const { response: myReviewList } = useFetch([], requestGetAllReviewList);

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  return (
    <Container>
      <Title>좋아요 표시한 글</Title>
      <Frame styles={frameStyle}>
        <MyLikeReviewList>
          {myLikeReviewList?.map((myLikeReview) => (
            <ReviewItem
              key={myLikeReview.id}
              review={myLikeReview}
              accessToken={accessToken}
              onClick={() => goReviewDetailPage(myLikeReview.id)}
            />
          ))}
        </MyLikeReviewList>
      </Frame>
    </Container>
  );
};

export default MyPageLikeReview;
