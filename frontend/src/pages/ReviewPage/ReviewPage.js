import { useState, useEffect } from 'react';
import {
  VACCINATION,
  PATH,
  RESPONSE_STATE,
  ALERT_MESSAGE,
  SNACKBAR_MESSAGE,
} from '../../constants';
import { Container, Title, ReviewList, FrameContent, ButtonWrapper } from './ReviewPage.styles';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getAllReviewListAsync, getSelectedReviewListAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Frame, Tabs } from '../../components/common';
import { ReviewItem, ReviewWritingModal } from '../../components';
import { useSnackBar } from '../../hooks';

const ReviewPage = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [selectedTab, setSelectedTab] = useState('전체');
  const [isModalOpen, setModalOpen] = useState(false);
  const [reviewList, setReviewList] = useState([]);

  const { isSnackBarOpen, openSnackBar, SnackBar } = useSnackBar();

  const tabList = ['전체', ...Object.values(VACCINATION)];

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

  const getReviewList = async () => {
    if (selectedTab === '전체') {
      const response = await getAllReviewListAsync(accessToken);

      if (response.state === RESPONSE_STATE.FAILURE) {
        alert('failure - getAllReviewListAsync');

        return;
      }

      setReviewList(response.data);
    } else {
      const vaccinationType = findKey(VACCINATION, selectedTab);
      const response = await getSelectedReviewListAsync(accessToken, vaccinationType);

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
                  accessToken={accessToken}
                  getReviewList={getReviewList}
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
          openSnackBar={openSnackBar}
          onClickClose={() => setModalOpen(false)}
        />
      )}
      {isSnackBarOpen && <SnackBar>{SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_REVIEW}</SnackBar>}
    </>
  );
};

export default ReviewPage;
