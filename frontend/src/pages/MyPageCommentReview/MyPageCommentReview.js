import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { ReviewItem } from '../../components';
import { Frame } from '../../components/common';
import { PATH } from '../../constants';
import { useFetch } from '../../hooks';
import { requestGetAllReviewList } from '../../requests';
import {
  Container,
  MyCommentReviewListContainer,
  Title,
  MyCommentReviewList,
  frameStyle,
} from './MyPageCommentReview.styles';
import myCommentReviewList from '../../fixtures/reviewList.json';
import MyCommentItem from '../../components/MyCommentItem/MyCommentItem';

const MyPageCommentReview = () => {
  const history = useHistory();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  // const { response: myReviewList } = useFetch([], requestGetAllReviewList);

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  return (
    <Container>
      <Title>댓글 단 글</Title>
      <Frame styles={frameStyle}>
        <MyCommentReviewListContainer>
          {myCommentReviewList?.map((myCommentReview) => {
            const myComments = myCommentReview.comments.filter(
              (comment) => comment.writer.id === user.id,
            );

            return myComments.map((myComment) => (
              <MyCommentReviewList>
                <MyCommentItem
                  key={myCommentReview.id.toString().concat('-', myComment.id.toString())}
                  myCommentReview={myCommentReview}
                  myComment={myComment}
                  onClick={() => goReviewDetailPage(myCommentReview.id)}
                />
              </MyCommentReviewList>
            ));
          })}
        </MyCommentReviewListContainer>
      </Frame>
    </Container>
  );
};

export default MyPageCommentReview;
