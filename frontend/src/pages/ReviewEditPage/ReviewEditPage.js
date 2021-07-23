import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { useSnackbar } from 'notistack';
import Button from '../../components/Button/Button';
import Frame from '../../components/Frame/Frame';
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  FONT_COLOR,
  PATH,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
  TO_DATE_TYPE,
  VACCINATION,
  VACCINATION_COLOR,
} from '../../constants';
import { useFetch } from '../../hooks';
import { requestGetReview } from '../../requests';
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
import Avatar from '../../components/Avatar/Avatar';
import toDate from '../../utils/toDate';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';
import { LABEL_SIZE_TYPE } from '../../components/Label/Label.styles';
import Label from '../../components/Label/Label';
import { putReviewAsync } from '../../service';
import { ClockIcon, EyeIcon, LeftArrowIcon } from '../../assets/icons';

const ReviewEditPage = () => {
  const history = useHistory();
  const { id } = useParams();
  const accessToken = useSelector((state) => state.authReducer.accessToken);
  const { enqueueSnackbar } = useSnackbar();

  const [content, setContent] = useState('');
  const { response: review, error } = useFetch({}, () => requestGetReview(id));

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const goBack = () => {
    if (!window.confirm(CONFIRM_MESSAGE.GO_BACK)) return;

    history.goBack();
  };

  const goReviewDetailPage = () => {
    history.push(`${PATH.REVIEW}/${id}`);
  };

  const editReview = async () => {
    const data = { content, vaccinationType: review?.vaccinationType };

    const response = await putReviewAsync(accessToken, id, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT);

      return;
    }

    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_REVIEW);
    goReviewDetailPage();
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
