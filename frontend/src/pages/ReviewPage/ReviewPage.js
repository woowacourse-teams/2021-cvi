import { useState, useEffect } from 'react';
import Frame from '../../components/Frame/Frame';
import ReviewItem from '../../components/ReviewItem/ReviewItem';
import Tabs from '../../components/Tabs/Tabs';
import Button from '../../components/Button/Button';
import ReviewWritingModal from '../../components/ReviewWritingModal/ReviewWritingModal';
import { VACCINATION } from '../../constants';
import { Container, Title, ReviewList, FrameContent, ButtonWrapper } from './ReviewPage.styles';
import { BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';
import { requestGetReviewList, requestGetSelectedVaccinationReviewList } from '../../requests';

const ReviewPage = () => {
  const [selectedTab, setSelectedTab] = useState('전체');
  const [isModalOpen, setModalOpen] = useState(false);
  const [reviewList, setReviewList] = useState([]);

  const tabList = ['전체', ...Object.values(VACCINATION)];

  useEffect(() => {
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

    if (selectedTab === '전체') {
      getReviewList(requestGetReviewList);
    } else {
      const selectedVaccination =
        Object.keys(VACCINATION)[Object.values(VACCINATION).indexOf(selectedTab)];

      getReviewList(() => requestGetSelectedVaccinationReviewList(selectedVaccination));
    }
  }, [selectedTab]);

  return (
    <>
      <Container>
        <Title>접종 후기</Title>
        <ButtonWrapper>
          <Button
            type="button"
            sizeType={BUTTON_SIZE_TYPE.LARGE}
            onClick={() => setModalOpen(true)}
          >
            후기 작성
          </Button>
        </ButtonWrapper>
        <Frame width="100%">
          <FrameContent>
            <Tabs tabList={tabList} selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
            <ReviewList>
              {reviewList?.map((review) => (
                <ReviewItem key={review.id} review={review} />
              ))}
            </ReviewList>
          </FrameContent>
        </Frame>
      </Container>
      {isModalOpen && <ReviewWritingModal onClickClose={() => setModalOpen(false)} />}
    </>
  );
};

export default ReviewPage;
