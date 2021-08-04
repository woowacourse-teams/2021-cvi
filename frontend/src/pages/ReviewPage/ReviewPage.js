import { useState, useEffect, useCallback } from 'react';
import { VACCINATION, PATH, RESPONSE_STATE, ALERT_MESSAGE, THEME_COLOR } from '../../constants';
import {
  Container,
  Title,
  ReviewList,
  FrameContent,
  ButtonWrapper,
  ScrollLoadingContainer,
} from './ReviewPage.styles';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getAllReviewListAsync, getSelectedReviewListAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Frame, Tabs } from '../../components/common';
import { ReviewItem, ReviewWritingModal } from '../../components';
import { useLoading } from '../../hooks';
import { useInView } from 'react-intersection-observer';

const ReviewPage = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer?.accessToken);
  const [ref, inView] = useInView();

  const [selectedTab, setSelectedTab] = useState('전체');
  const [isModalOpen, setModalOpen] = useState(false);
  const [reviewList, setReviewList] = useState([]);
  const [lastPostId, setLastPostId] = useState(Number.MAX_SAFE_INTEGER);

  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const {
    showLoading: showScrollLoading,
    hideLoading: hideScrollLoading,
    isLoading: isScrollLoading,
    Loading: ScrollLoading,
  } = useLoading();

  const tabList = ['전체', ...Object.values(VACCINATION)];
  const isLastPost = (index) => index === reviewList?.length - 1;

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  const goLoginPage = () => {
    history.push(`${PATH.LOGIN}`);
  };

  const onClickButton = () => {
    if (accessToken) {
      setModalOpen(true);
    } else {
      if (!window.confirm(ALERT_MESSAGE.NEED_LOGIN)) return;

      goLoginPage();
    }
  };

  const getReviewList = useCallback(async () => {
    if (selectedTab === '전체') {
      const response = await getAllReviewListAsync(accessToken, lastPostId);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getAllReviewListAsync');

        return;
      }

      setReviewList((prevState) => [...prevState, ...response.data]);
    } else {
      const vaccinationType = findKey(VACCINATION, selectedTab);
      const response = await getSelectedReviewListAsync(accessToken, vaccinationType, lastPostId);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getSelectedReviewListAsync');

        return;
      }

      setReviewList((prevState) => [...prevState, ...response.data]);
    }

    hideLoading();
    hideScrollLoading();
  }, [lastPostId, selectedTab]);

  useEffect(() => {
    getReviewList();
  }, [getReviewList]);

  useEffect(() => {
    showLoading();

    setLastPostId(Number.MAX_SAFE_INTEGER);
    setReviewList([]);
  }, [selectedTab]);

  useEffect(() => {
    if (!inView) return;

    setLastPostId(reviewList[reviewList.length - 1]?.id);
    showScrollLoading();
  }, [inView]);

  return (
    <>
      <Container>
        <Title>접종 후기</Title>
        <ButtonWrapper>
          <Button type="button" sizeType={BUTTON_SIZE_TYPE.LARGE} onClick={onClickButton}>
            후기 작성
          </Button>
        </ButtonWrapper>
        <Frame width="100%" showShadow={true}>
          <FrameContent>
            <Tabs tabList={tabList} selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
            <ReviewList>
              {isLoading ? (
                <Loading isLoading={isLoading} backgroundColor={THEME_COLOR.WHITE} />
              ) : (
                reviewList?.map((review, index) => (
                  <ReviewItem
                    key={review.id}
                    review={review}
                    accessToken={accessToken}
                    getReviewList={getReviewList}
                    innerRef={isLastPost(index) ? ref : null}
                    onClick={() => goReviewDetailPage(review.id)}
                  />
                ))
              )}
            </ReviewList>
            {isScrollLoading && (
              <ScrollLoadingContainer>
                <ScrollLoading isLoading={isScrollLoading} width="4rem" height="4rem" />
              </ScrollLoadingContainer>
            )}
          </FrameContent>
        </Frame>
      </Container>
      {isModalOpen && (
        <ReviewWritingModal
          getReviewList={getReviewList}
          setLastPostId={setLastPostId}
          setReviewList={setReviewList}
          onClickClose={() => setModalOpen(false)}
        />
      )}
    </>
  );
};

export default ReviewPage;
