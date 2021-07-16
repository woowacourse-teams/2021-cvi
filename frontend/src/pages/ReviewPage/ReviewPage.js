import { useState, useEffect } from 'react';
import Frame from '../../components/Frame/Frame';
import ReviewItem from '../../components/ReviewItem/ReviewItem';
import Tabs from '../../components/Tabs/Tabs';
import Button from '../../components/Button/Button';
import ReviewWritingModal from '../../components/ReviewWritingModal/ReviewWritingModal';
import { VACCINATION, PATH } from '../../constants';
import { Title, ReviewList, FrameContent, ButtonWrapper } from './ReviewPage.styles';
import { BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';
import { requestGetAllReviewList, requestGetSelectedReviewList } from '../../requests';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';

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

  const getReviewList = async (callback) => {
    try {
      const response = await callback();

      if (!response.ok) {
        throw new Error(response.status);
      }

      setReviewList(await response.json());
    } catch (error) {
      console.error(error);
    }
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

  useEffect(() => {
    if (selectedTab === '전체') {
      getReviewList(requestGetAllReviewList);
    } else {
      const selectedVaccination =
        Object.keys(VACCINATION)[Object.values(VACCINATION).indexOf(selectedTab)];

      getReviewList(() => requestGetSelectedReviewList(selectedVaccination));
    }
  }, [selectedTab]);

  return (
    <>
      <div>
        <Title>접종 후기</Title>
        <ButtonWrapper>
          <Button type="button" sizeType={BUTTON_SIZE_TYPE.LARGE} onClick={onClickButton}>
            후기 작성
          </Button>
        </ButtonWrapper>
        <Frame width="100%">
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
      </div>
      {isModalOpen && (
        <ReviewWritingModal
          selectedTab={selectedTab}
          getReviewList={getReviewList}
          onClickClose={() => setModalOpen(false)}
        />
      )}
    </>
  );
};

export default ReviewPage;
