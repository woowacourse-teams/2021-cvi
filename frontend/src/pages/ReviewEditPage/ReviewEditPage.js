import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import Button from '../../components/Button/Button';
import Frame from '../../components/Frame/Frame';
import { FONT_COLOR, PATH, TO_DATE_TYPE, VACCINATION, VACCINATION_COLOR } from '../../constants';
import { useFetch } from '../../hooks';
import { requestGetReview, requestPutReview } from '../../requests';
import {
  Container,
  FrameContent,
  ButtonContainer,
  Info,
  VaccinationInfo,
  ReviewInfo,
  ShotVerified,
  WriterInfo,
  Writer,
  InfoBottom,
  CreatedAt,
  TextArea,
  ViewCount,
  editButtonStyles,
} from './ReviewEditPage.styles';
import LeftArrowIcon from '../../assets/icons/left-arrow.svg';
import Avatar from '../../components/Avatar/Avatar';
import toDate from '../../utils/toDate';
import ClockIcon from '../../assets/icons/clock.svg';
import EyeIcon from '../../assets/icons/eye.svg';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';
import { LABEL_SIZE_TYPE } from '../../components/Label/Label.styles';
import Label from '../../components/Label/Label';

const ReviewEditPage = () => {
  const history = useHistory();
  const { id } = useParams();
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [content, setContent] = useState('');
  const { response: review, error } = useFetch({}, () => requestGetReview(id));

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const goBack = () => {
    history.goBack();
  };

  const goReviewDetailPage = () => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  const editReview = async () => {
    const data = { content, vaccinationType: review?.vaccinationType };

    try {
      const response = await requestPutReview(accessToken, id, data);

      if (!response.ok) {
        throw new Error();
      }

      goReviewDetailPage();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Container>
      <Frame width="100%" showShadow={true}>
        <FrameContent>
          <ButtonContainer>
            <Button
              sizeType={BUTTON_SIZE_TYPE.LARGE}
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              color={FONT_COLOR.BLACK}
              withIcon={true}
              onClick={goBack}
            >
              <LeftArrowIcon width="18" height="18" stroke={FONT_COLOR.BLACK} />
              <div>뒤로 가기</div>
            </Button>
          </ButtonContainer>
          <Info>
            <VaccinationInfo>
              <Label
                backgroundColor={VACCINATION_COLOR[review.vaccinationType]}
                sizeType={LABEL_SIZE_TYPE.MEDIUM}
                fontColor={labelFontColor}
              >
                {VACCINATION[review.vaccinationType]}
              </Label>
              <ShotVerified>{review?.writer?.shotVerified && '접종 확인'}</ShotVerified>
            </VaccinationInfo>
            <WriterInfo>
              <Avatar />
              <Writer>
                {review?.writer?.nickname} · {review?.writer?.ageRange?.meaning}
              </Writer>
            </WriterInfo>
            <InfoBottom>
              <ReviewInfo>
                <ClockIcon width="16" height="16" stroke={FONT_COLOR.LIGHT_GRAY} />
                {review.createdAt && (
                  <CreatedAt>{toDate(TO_DATE_TYPE.TIME, review.createdAt)}</CreatedAt>
                )}
                <EyeIcon width="18" height="18" stroke={FONT_COLOR.LIGHT_GRAY} />
                <ViewCount>{review?.viewCount}</ViewCount>
              </ReviewInfo>
            </InfoBottom>
          </Info>
          <TextArea onChange={(event) => setContent(event.target.value)}>
            {review?.content}
          </TextArea>
        </FrameContent>
      </Frame>
      <Button
        backgroundType={BUTTON_BACKGROUND_TYPE.FILLED}
        sizeType={BUTTON_SIZE_TYPE.LARGE}
        styles={editButtonStyles}
        onClick={editReview}
      >
        수정하기
      </Button>
    </Container>
  );
};

export default ReviewEditPage;
