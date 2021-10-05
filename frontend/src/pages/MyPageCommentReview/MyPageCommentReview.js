import { useSelector } from 'react-redux';
import { Frame, LottieAnimation } from '../../components/@common';
import { PAGING_SIZE, RESPONSE_STATE, THEME_COLOR, TO_DATE_TYPE } from '../../constants';
import { useLoading, useMovePage } from '../../hooks';
import {
  Container,
  LottieContainer,
  LoadingContainer,
  ScrollLoadingContainer,
  MyCommentReviewList,
  MyCommentReviewItem,
  Title,
  frameStyle,
  CreatedAt,
  CommentContent,
} from './MyPageCommentReview.styles';
import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { NotFoundAnimation } from '../../assets/lotties';
import { PreviewItem } from '../../components';
import { toDate } from '../../utils';
import customRequest from '../../service/customRequest';
import { fetchGetMyCommentReviewList } from '../../service/fetch';

const MyPageCommentReview = () => {
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
  const { goReviewDetailPage } = useMovePage();

  const isLastPost = (index) => index === offset + PAGING_SIZE - 1;

  const getMyCommentReviewList = useCallback(async () => {
    if (!accessToken) return;

    const response = await customRequest(() => fetchGetMyCommentReviewList(accessToken, offset));

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
            width="26rem"
            mobileWidth="18rem"
            designer="Radhikakpor"
            description="댓글 단 글이 없습니다"
          />
        </LottieContainer>
      ) : (
        <Frame styles={frameStyle}>
          <MyCommentReviewList>
            {myCommentReviewList?.map((myCommentReview, index) => {
              const myComments = myCommentReview.comments.filter(
                (comment) => comment.writer.id === user.id,
              );

              return (
                <MyCommentReviewItem key={myCommentReview.id} ref={isLastPost(index) ? ref : null}>
                  {myComments.map((myComment) => (
                    <div key={myComment.id}>
                      <CreatedAt>{toDate(TO_DATE_TYPE.TIME, myComment.createdAt)}</CreatedAt>
                      <CommentContent>{myComment.content}</CommentContent>
                    </div>
                  ))}
                  <PreviewItem
                    review={myCommentReview}
                    withShadow={true}
                    withImage={true}
                    onClick={() => goReviewDetailPage(myCommentReview.id)}
                  />
                </MyCommentReviewItem>
              );
            })}
          </MyCommentReviewList>
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
