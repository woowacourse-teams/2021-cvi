import { useState, useEffect } from 'react';
import { VACCINATION, PATH, RESPONSE_STATE } from '../../constants';
import { Container, Title, ReviewList, FrameContent, ButtonWrapper } from './ReviewPage.styles';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getAllReviewListAsync, getSelectedReviewListAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Frame, Tabs } from '../../components/common';
import { ReviewItem, ReviewWritingModal } from '../../components';

const ReviewPage = () => {
  const history = useHistory();
  const isLogin = !!useSelector((state) => state.authReducer?.accessToken);

  const [selectedTab, setSelectedTab] = useState('전체');
  const [isModalOpen, setModalOpen] = useState(false);
  const [reviewList, setReviewList] = useState([]);

  const tabList = ['전체', ...Object.values(VACCINATION)];

  const goReviewDetailPage = (id) => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  const goLoginPage = () => {
    history.push(`${PATH.LOGIN}`);
  };

  const onClickButton = () => {
    if (isLogin) {
      setModalOpen(true);
    } else {
      if (window.confirm('로그인이 필요합니다.')) {
        goLoginPage();
      } else {
        return;
      }
    }
  };

  const getReviewList = async () => {
    if (selectedTab === '전체') {
      const response = await getAllReviewListAsync();

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getAllReviewListAsync');

        return;
      }

      setReviewList(response.data);
    } else {
      const vaccinationType = findKey(VACCINATION, selectedTab);
      const response = await getSelectedReviewListAsync(vaccinationType);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getSelectedReviewListAsync');

        return;
      }

      setReviewList(response.data);
    }
  };

  useEffect(() => {
    getReviewList();
  }, [selectedTab]);

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
              {reviewList?.map((review) => (
                <ReviewItem
                  key={review.id}
                  review={review}
                  onClick={() => goReviewDetailPage(review.id)}
                />
              ))}
            </ReviewList>
          </FrameContent>
        </Frame>
      </Container>
      {isModalOpen && (
        <ReviewWritingModal
          getReviewList={getReviewList}
          onClickClose={() => setModalOpen(false)}
        />
      )}
    </>
  );
};

export default ReviewPage;
