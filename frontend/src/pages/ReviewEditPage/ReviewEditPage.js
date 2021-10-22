import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import Compressor from 'compressorjs';
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  FONT_COLOR,
  RESPONSE_STATE,
  REVIEW_IMAGE_LIMIT,
  SNACKBAR_MESSAGE,
  TO_DATE_TYPE,
  VACCINATION,
  VACCINATION_COLOR,
} from '../../constants';
import { useFetch, useMovePage, useSnackbar } from '../../hooks';
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
  inputStyles,
  labelStyles,
} from './ReviewEditPage.styles';
import toDate from '../../utils/toDate';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/@common/Button/Button.styles';
import { LABEL_SIZE_TYPE } from '../../components/@common/Label/Label.styles';
import { ClockIcon, EyeIcon, LeftArrowIcon } from '../../assets/icons';
import { Avatar, Button, Frame, Input, Label } from '../../components/@common';
import { ReviewImage } from '../../components';
import { fetchGetImage, fetchGetReview, fetchPutReview } from '../../service/fetch';
import customRequest from '../../service/customRequest';

const ReviewEditPage = () => {
  const { id } = useParams();
  const accessToken = useSelector((state) => state.authReducer.accessToken);
  const user = useSelector((state) => state.authReducer.user);

  const [content, setContent] = useState('');
  const [images, setImages] = useState([]);

  const { response: review } = useFetch({}, () => fetchGetReview(accessToken, id));
  const { openSnackbar } = useSnackbar();
  const { goReviewDetailPage, goPreviousPage } = useMovePage();

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const goBack = () => {
    if (!window.confirm(CONFIRM_MESSAGE.GO_BACK)) return;

    goPreviousPage();
  };

  const changeImageToBase64 = async (images) =>
    images?.forEach(async (image) => {
      const response = await fetchGetImage(image);
      const blobData = await response.blob();

      const reader = new FileReader();

      reader.readAsDataURL(blobData);
      reader.onload = () => {
        setImages((prevState) => [...prevState, reader.result]);
      };
    });

  const updateImageFormat = (image) => {
    const [type, data] = image.split(',');

    const startIndex = type.indexOf('/');
    const endIndex = type.indexOf(';');

    return { type: type.substring(startIndex + 1, endIndex).toUpperCase(), data };
  };

  const editReview = async () => {
    if (!content) {
      alert(ALERT_MESSAGE.NEED_REVIEW_CONTENT);

      return;
    }

    const updatedImageFormat = images.map((image) => updateImageFormat(image));

    const data = { content, vaccinationType: review?.vaccinationType, images: updatedImageFormat };

    const response = await customRequest(() => fetchPutReview(accessToken, id, data));

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT);

      return;
    }

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_REVIEW);
    goReviewDetailPage(id);
  };

  const deleteImage = async (src) => {
    setImages((prevState) => prevState.filter((state) => state !== src));
  };

  const uploadImages = (event) => {
    if (images.length === REVIEW_IMAGE_LIMIT) {
      alert(ALERT_MESSAGE.OVER_IMAGE_COUNT);

      return;
    }

    let files = [...event.target.files];
    const leftImageCount = REVIEW_IMAGE_LIMIT.MAX_COUNT - images.length;

    if (files.length > leftImageCount) {
      files = files.slice(0, leftImageCount);

      alert(ALERT_MESSAGE.OVER_IMAGE_COUNT);
    }

    for (let file of files) {
      if (file.size > REVIEW_IMAGE_LIMIT.MAX_SIZE) {
        alert(ALERT_MESSAGE.OVER_IMAGE_SIZE);

        continue;
      }

      new Compressor(file, {
        maxWidth: 1200,
        quality: 1,
        strict: true,
        success: (result) => {
          const reader = new FileReader();

          reader.readAsDataURL(result);
          reader.onload = () => {
            setImages((prevState) => [...prevState, reader.result]);
          };
        },
        error: (error) => {
          console.log(error.message);
        },
      });
    }
  };

  useEffect(() => {
    if (Object.keys(review).length && user?.id !== review?.writer?.id) {
      alert(ALERT_MESSAGE.FAIL_TO_ACCESS_EDIT_PAGE);

      goPreviousPage();
    }

    setContent(review?.content);
    changeImageToBase64(review?.images);
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
          <TextArea value={content} onChange={(event) => setContent(event.target.value)} />
          <ImageContainer>
            {images?.map((image, index) => (
              <ReviewImage
                key={index}
                src={image}
                showCloseIcon={true}
                onClickDeleteButton={() => deleteImage(image)}
              />
            ))}
          </ImageContainer>
          {images?.length < 5 && (
            <Input
              type="file"
              name="review-image"
              multiple
              accept="image/jpg, image/png, image/jpeg"
              inputStyles={inputStyles}
              labelStyles={labelStyles}
              labelText="+"
              onChange={uploadImages}
            />
          )}
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
