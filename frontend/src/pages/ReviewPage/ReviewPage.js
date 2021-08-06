import { useState, useEffect, useCallback } from 'react';
import {
  VACCINATION,
  PATH,
  RESPONSE_STATE,
  ALERT_MESSAGE,
  THEME_COLOR,
  PAGING_SIZE,
  FONT_COLOR,
} from '../../constants';
import {
  Container,
  Title,
  ReviewList,
  FrameContent,
  ButtonWrapper,
  ScrollLoadingContainer,
  TabContainer,
  tabFrameStyles,
  filterButtonStyles,
} from './ReviewPage.styles';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/common/Button/Button.styles';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getAllReviewListAsync, getSelectedReviewListAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Frame, OptionList, Tabs } from '../../components/common';
import { ReviewItem, ReviewWritingModal } from '../../components';
import { useLoading } from '../../hooks';
import { useInView } from 'react-intersection-observer';
import { SELECTED_TAB_STYLE_TYPE } from '../../components/common/Tabs/Tabs.styles';
import { FilterIcon } from '../../assets/icons';

const ReviewPage = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [selectedVaccination, setSelectedVaccination] = useState('전체');
  const [selectedFilter, setSelectedFilter] = useState('최신순');
  const [selectedSort, setSelectedSort] = useState('내림차순');
  const [isModalOpen, setModalOpen] = useState(false);
  const [reviewList, setReviewList] = useState([]);
  const [offset, setOffset] = useState(0);
  const [isOptionListShowing, setIsOptionListShowing] = useState(0);

  const [ref, inView] = useInView();
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const {
    showLoading: showScrollLoading,
    hideLoading: hideScrollLoading,
    isLoading: isScrollLoading,
    Loading: ScrollLoading,
  } = useLoading();

  const vaccineList = ['전체', ...Object.values(VACCINATION)];
  const filterList = ['최신순', '좋아요순', '조회수순'];
  const sortList = ['오름차순', '내림차순'];
  const isLastPost = (index) => index === offset + PAGING_SIZE - 1;

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

  const showOptionList = () => {
    setIsOptionListShowing(true);
  };

  const hideOptionList = () => {
    setIsOptionListShowing(false);
  };

  const getReviewList = useCallback(async () => {
    if (selectedVaccination === '전체') {
      const response = await getAllReviewListAsync(accessToken, offset);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getAllReviewListAsync');

        return;
      }

      setReviewList((prevState) => [...prevState, ...response.data]);
    } else {
      const vaccinationType = findKey(VACCINATION, selectedVaccination);
      const response = await getSelectedReviewListAsync(accessToken, vaccinationType, offset);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getSelectedReviewListAsync');

        return;
      }

      setReviewList((prevState) => [...prevState, ...response.data]);
    }

    hideLoading();
    hideScrollLoading();
  }, [offset, selectedVaccination]);

  useEffect(() => {
    getReviewList();
  }, [getReviewList]);

  useEffect(() => {
    showLoading();

    setOffset(0);
    setReviewList([]);
  }, [selectedVaccination]);

  useEffect(() => {
    if (!inView) return;

    setOffset((prevState) => prevState + PAGING_SIZE);
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
        <Frame width="100%" showShadow={true} styles={tabFrameStyles}>
          {isOptionListShowing ? (
            <OptionList
              vaccineList={vaccineList}
              filterList={filterList}
              sortList={sortList}
              selectedVaccination={selectedVaccination}
              selectedFilter={selectedFilter}
              selectedSort={selectedSort}
              setSelectedVaccination={setSelectedVaccination}
              setSelectedFilter={setSelectedFilter}
              setSelectedSort={setSelectedSort}
              hideOptionList={hideOptionList}
            />
          ) : (
            <TabContainer>
              <Tabs
                tabList={vaccineList}
                selectedTab={selectedVaccination}
                selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
                setSelectedTab={setSelectedVaccination}
              />
              <Button
                backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                sizeType={BUTTON_SIZE_TYPE.LARGE}
                color={FONT_COLOR.BLACK}
                withIcon={true}
                styles={filterButtonStyles}
                onClick={showOptionList}
              >
                <FilterIcon height="16" width="16" />
                <div>옵션</div>
              </Button>
            </TabContainer>
          )}
        </Frame>
        <Frame width="100%" showShadow={true}>
          <FrameContent>
            <ReviewList>
              {isLoading ? (
                <Loading isLoading={isLoading} backgroundColor={THEME_COLOR.WHITE} />
              ) : (
                reviewList?.map((review, index) => (
                  <ReviewItem
                    key={review.id}
                    review={review}
                    accessToken={accessToken}
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
          setOffset={setOffset}
          setReviewList={setReviewList}
          onClickClose={() => setModalOpen(false)}
        />
      )}
    </>
  );
};

export default ReviewPage;
