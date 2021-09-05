import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
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
import { useFetch, useSnackBar } from '../../hooks';
import { requestGetReview } from '../../requests';
import {
  Container,
  FrameContent,
  ButtonContainer,
  Info,
  VaccinationInfo,
  ReviewInfo,
  WriterInfo,
  Writer,
  InfoBottom,
  CreatedAt,
  TextArea,
  ViewCount,
  editButtonStyles,
  EditButtonContainer,
  ImageContainer,
} from './ReviewEditPage.styles';
import toDate from '../../utils/toDate';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/common/Button/Button.styles';
import { LABEL_SIZE_TYPE } from '../../components/common/Label/Label.styles';
import { putReviewAsync } from '../../service';
import { ClockIcon, EyeIcon, LeftArrowIcon } from '../../assets/icons';
import { Avatar, Button, Frame, Label } from '../../components/common';
import { ReviewImage } from '../../components';
import example from '../../assets/images/calendar.png';
import defaultt from '../../assets/images/default_profile.png';
import vaccineImage from '../../assets/images/vaccination_example.png';

const ReviewEditPage = () => {
  const history = useHistory();
  const { id } = useParams();
  const accessToken = useSelector((state) => state.authReducer.accessToken);
  const user = useSelector((state) => state.authReducer.user);

  const [content, setContent] = useState('');
  const [images, setImages] = useState([]);

  const { response: review } = useFetch({}, () => requestGetReview(accessToken, id));
  const { openSnackBar } = useSnackBar();

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

    openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_REVIEW);
    goReviewDetailPage();
  };

  const deleteImage = async (index) => {
    const url = 'http://localhost:9000/e4ad4523f0abddbd3b1b.png';
    const response = await fetch(url);
    const data = await response.blob();

    return new Promise((resolve) => {
      const reader = new FileReader();
      reader.readAsDataURL(data);
      reader.onloadend = () => {
        const base64data = reader.result;
        console.log(base64data);
        resolve(base64data);
      };
    });
  };

  useEffect(() => {
    if (Object.keys(review).length && user?.id !== review?.writer?.id) {
      alert(ALERT_MESSAGE.FAIL_TO_ACCESS_EDIT_PAGE);
      history.goBack();
    }

    setContent(review?.content);
  }, [review]);

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
            </VaccinationInfo>
            <WriterInfo>
              <Avatar src={review?.writer?.socialProfileUrl} />
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
          <TextArea value={content} onChange={(event) => setContent(event.target.value)}>
            {review?.content}
          </TextArea>
          <ImageContainer>
            <ReviewImage
              src={vaccineImage}
              width="24rem"
              showCloseIcon={true}
              onClickDeleteButton={() => deleteImage(0)}
            />
            <ReviewImage
              src={defaultt}
              width="24rem"
              showCloseIcon={true}
              onClickDeleteButton={() => deleteImage(1)}
            />
            <ReviewImage
              src={example}
              width="24rem"
              showCloseIcon={true}
              onClickDeleteButton={() => deleteImage(2)}
            />
            <ReviewImage
              src={defaultt}
              width="24rem"
              showCloseIcon={true}
              onClickDeleteButton={() => deleteImage(3)}
            />
            <ReviewImage
              src={example}
              width="24rem"
              showCloseIcon={true}
              onClickDeleteButton={() => deleteImage(4)}
            />
          </ImageContainer>
        </FrameContent>
      </Frame>
      <EditButtonContainer>
        <Button
          backgroundType={BUTTON_BACKGROUND_TYPE.FILLED}
          sizeType={BUTTON_SIZE_TYPE.LARGE}
          styles={editButtonStyles}
          onClick={editReview}
        >
          수정하기
        </Button>
      </EditButtonContainer>
    </Container>
  );
};

export default ReviewEditPage;
