import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { ReviewItem } from '../../components';
import { Frame } from '../../components/common';
import { PAGING_SIZE, PATH, RESPONSE_STATE, THEME_COLOR } from '../../constants';
import { useLoading } from '../../hooks';
import {
  Container,
  ScrollLoadingContainer,
  Title,
  MyReviewList,
  frameStyle,
} from './MyPageReview.styles';
import { useCallback, useEffect, useState } from 'react';
import { getMyReviewListAsync } from '../../service';
import { useInView } from 'react-intersection-observer';

const MyPageReview = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [myReviewList, setMyReviewList] = useState([]);
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

  const getMyReviewList = useCallback(async () => {
    if (!accessToken) return;

    const response = await getMyReviewListAsync(accessToken, offset);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert('failure - getMyReviewListAsync');

      return;
    }

    hideLoading();
    hideScrollLoading();
    setMyReviewList((prevState) => [...prevState, ...response.data]);
  }, [offset, accessToken]);

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  useEffect(() => {
    // 게시글 아무것도 없을 때 처리
    if (offset === 0) showLoading();

    getMyReviewList();
  }, [getMyReviewList]);

  useEffect(() => {
    if (!inView) return;

    setOffset((prevState) => prevState + PAGING_SIZE);
    showScrollLoading();
  }, [inView]);

  return (
    <Container>
      <Title>내가 쓴 글</Title>
      <Frame styles={frameStyle}>
        {isLoading ? (
          <Loading isLoading={isLoading} backgroundColor={THEME_COLOR.WHITE} />
        ) : (
          <MyReviewList>
            {myReviewList?.map((myReview, index) => (
              <ReviewItem
                key={myReview.id}
                review={myReview}
                accessToken={accessToken}
                innerRef={isLastPost(index) ? ref : null}
                onClick={() => goReviewDetailPage(myReview.id)}
              />
            ))}
          </MyReviewList>
        )}
        {isScrollLoading && (
          <ScrollLoadingContainer>
            <ScrollLoading isLoading={isScrollLoading} width="4rem" height="4rem" />
          </ScrollLoadingContainer>
        )}
      </Frame>
    </Container>
  );
};

export default MyPageReview;
