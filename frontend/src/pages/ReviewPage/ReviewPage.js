import { useState } from 'react';
import Frame from '../../components/Frame/Frame';
import ReviewItem from '../../components/ReviewItem/ReviewItem';
import Tabs from '../../components/Tabs/Tabs';
import Button from '../../components/Button/Button';
import { VACCINE } from '../../constants';
import { Container, Title, ReviewList, FrameContent, ButtonWrapper } from './ReviewPage.styles';
import db from '../../db.json';
import { BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';

const ReviewPage = () => {
  const [selectedTab, setSelectedTab] = useState('전체');

  const tabList = ['전체', ...Object.keys(VACCINE)];

  return (
    <Container>
      <Title>접종 후기</Title>
      <ButtonWrapper>
        <Button type="button" sizeType={BUTTON_SIZE_TYPE.LARGE}>
          후기 작성
        </Button>
      </ButtonWrapper>
      <Frame width="100%">
        <FrameContent>
          <Tabs tabList={tabList} selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
          <ReviewList>
            {db?.reviewList.map((review) => (
              <ReviewItem key={review.id} review={review} />
            ))}
          </ReviewList>
        </FrameContent>
      </Frame>
    </Container>
  );
};

export default ReviewPage;
