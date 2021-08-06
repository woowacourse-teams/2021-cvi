import { useState, useEffect, useCallback } from 'react';
import {
  VACCINATION,
  PATH,
  RESPONSE_STATE,
  ALERT_MESSAGE,
  THEME_COLOR,
  PAGING_SIZE,
} from '../../constants';
import {
  Container,
  Title,
  ReviewList,
  FrameContent,
  ButtonWrapper,
  ScrollLoadingContainer,
  TabContainer,
} from './ReviewPage.styles';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/common/Button/Button.styles';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getAllReviewListAsync, getSelectedReviewListAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Dropdown, Frame, Tabs } from '../../components/common';
import { ReviewItem, ReviewWritingModal } from '../../components';
import { useLoading } from '../../hooks';
import { useInView } from 'react-intersection-observer';

const ReviewPage = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [selectedTab, setSelectedTab] = useState('전체');
  const [selectedFilter, setSelectedFilter] = useState('최신순');
  const [isModalOpen, setModalOpen] = useState(false);
  const [reviewList, setReviewList] = useState([]);
  const [offset, setOffset] = useState(0);
  const [isDropdownVisible, setIsDropdownVisible] = useState(false);

  const [ref, inView] = useInView();
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const {
    showLoading: showScrollLoading,
    hideLoading: hideScrollLoading,
    isLoading: isScrollLoading,
    Loading: ScrollLoading,
  } = useLoading();

  const tabList = ['전체', ...Object.values(VACCINATION)];
  const dropdownMenuList = ['최신순', '좋아요순', '댓글 개수 순', '조회수순'];
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

  const showDropdown = () => {
    setIsDropdownVisible(true);
  };

  const hideDropdown = (event) => {
    if (event.currentTarget === event.target) {
      setIsDropdownVisible(false);
    }
  };

  const getReviewList = useCallback(async () => {
    if (selectedTab === '전체') {
      const response = await getAllReviewListAsync(accessToken, offset);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getAllReviewListAsync');

        return;
      }

      setReviewList((prevState) => [...prevState, ...response.data]);
    } else {
      const vaccinationType = findKey(VACCINATION, selectedTab);
      const response = await getSelectedReviewListAsync(accessToken, vaccinationType, offset);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getSelectedReviewListAsync');

        return;
      }

      setReviewList((prevState) => [...prevState, ...response.data]);
    }

    hideLoading();
    hideScrollLoading();
  }, [offset, selectedTab]);

  useEffect(() => {
    getReviewList();
  }, [getReviewList]);

  useEffect(() => {
    showLoading();

    setOffset(0);
    setReviewList([]);
  }, [selectedTab]);

  useEffect(() => {
    if (!inView) return;

    setOffset((prevState) => prevState + PAGING_SIZE);
    showScrollLoading();
  }, [inView]);

  return (
    <>
      <Container onClick={hideDropdown}>
        <Title>접종 후기</Title>
        <ButtonWrapper>
          <Button type="button" sizeType={BUTTON_SIZE_TYPE.LARGE} onClick={onClickButton}>
            후기 작성
          </Button>
        </ButtonWrapper>
        <Frame width="100%" showShadow={true}>
          <FrameContent>
            <TabContainer>
              <Tabs tabList={tabList} selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
              <Dropdown
                isDropdownVisible={isDropdownVisible}
                selectedFilter={selectedFilter}
                menuList={dropdownMenuList}
                onClick={showDropdown}
              />
            </TabContainer>
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
