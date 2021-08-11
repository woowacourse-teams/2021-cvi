import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { Frame, LottieAnimation } from '../../components/common';
import { PAGING_SIZE, PATH, RESPONSE_STATE, THEME_COLOR } from '../../constants';
import { useLoading } from '../../hooks';
import {
  Container,
  LottieContainer,
  LoadingContainer,
  ScrollLoadingContainer,
  MyCommentReviewListContainer,
  Title,
  frameStyle,
} from './MyPageCommentReview.styles';
import MyCommentsItem from '../../components/MyCommentsItem/MyCommentsItem';
import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { getMyCommentReviewListAsync } from '../../service';
import { NotFoundAnimation } from '../../assets/lotties';

const MyPageCommentReview = () => {
  const history = useHistory();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [myCommentReviewList, setMyCommentReviewList] = useState([]);
  const [offset, setOffset] = useState(0);

  const [ref, inView] = useInView();
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const {
    showLoading: showScrollLoading,
    hideLoading: hideScrollLoading,
    isLoading: isScrollLoading,
    Loading: ScrollLoading,
  } = useLoading();

  const isLastPost = (index) => index === offset + PAGING_SIZE - 1;

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  const getMyCommentReviewList = useCallback(async () => {
    if (!accessToken) return;

    const response = await getMyCommentReviewListAsync(accessToken, offset);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert('failure - getMyCommentReviewList');

      return;
    }

    hideLoading();
    hideScrollLoading();
    setMyCommentReviewList((prevState) => [...prevState, ...response.data]);
  }, [offset, accessToken]);

  useEffect(() => {
    if (offset === 0) showLoading();

    getMyCommentReviewList();
  }, [getMyCommentReviewList]);

  useEffect(() => {
    if (!inView) return;

    setOffset((prevState) => prevState + PAGING_SIZE);
    showScrollLoading();
  }, [inView]);

  return (
    <Container>
      <Title>댓글 단 글</Title>
      {isLoading ? (
        <LoadingContainer>
          <Loading isLoading={isLoading} backgroundColor={THEME_COLOR.WHITE} />
        </LoadingContainer>
      ) : !myCommentReviewList.length ? (
        <LottieContainer>
          <LottieAnimation
            data={NotFoundAnimation}
            width="40rem"
            mobileWidth="18rem"
            designer="Radhikakpor"
            description="댓글 단 글이 없습니다"
          />
        </LottieContainer>
      ) : (
        <Frame styles={frameStyle}>
          <MyCommentReviewListContainer>
            {myCommentReviewList?.map((myCommentReview, index) => {
              const myComments = myCommentReview.comments.filter(
                (comment) => comment.writer.id === user.id,
              );

              return (
                <MyCommentsItem
                  key={myCommentReview.id}
                  myCommentReview={myCommentReview}
                  myComments={myComments}
                  innerRef={isLastPost(index) ? ref : null}
                  onClick={() => goReviewDetailPage(myCommentReview.id)}
                />
              );
            })}
          </MyCommentReviewListContainer>
        </Frame>
      )}
      {isScrollLoading && (
        <ScrollLoadingContainer>
          <ScrollLoading isLoading={isScrollLoading} width="4rem" height="4rem" />
        </ScrollLoadingContainer>
      )}
    </Container>
  );
};

export default MyPageCommentReview;
