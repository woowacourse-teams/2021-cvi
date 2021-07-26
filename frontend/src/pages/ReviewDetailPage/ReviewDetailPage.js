import { useSelector } from 'react-redux';
import { useSnackbar } from 'notistack';
import {
  Container,
  FrameContent,
  ButtonContainer,
  TopContainer,
  VaccinationInfo,
  ReviewInfo,
  ShotVerified,
  WriterInfo,
  Writer,
  InfoBottom,
  UpdateButtonContainer,
  CreatedAt,
  Content,
  ViewCount,
  Error,
  buttonStyles,
  Comment,
  IconContainer,
  BottomContainer,
  CommentCount,
} from './ReviewDetailPage.styles';
import { useHistory, useParams } from 'react-router-dom';
import { useFetch } from '../../hooks';
import { requestGetReview } from '../../requests';
import { LABEL_SIZE_TYPE } from '../../components/common/Label/Label.styles';
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  ERROR_MESSAGE,
  FONT_COLOR,
  PATH,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
  TO_DATE_TYPE,
  VACCINATION,
  VACCINATION_COLOR,
} from '../../constants';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/common/Button/Button.styles';
import { toDate } from '../../utils';
import { ClockIcon, EyeIcon, LeftArrowIcon, CommentIcon, LikeIcon } from '../../assets/icons';
import { deleteReviewAsync } from '../../service';
import { Avatar, Button, Frame, Label } from '../../components/common';
import { CommentForm } from '../../components';

const ReviewDetailPage = () => {
  const history = useHistory();
  const { id } = useParams();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);
  const { enqueueSnackbar } = useSnackbar();

  const { response: review, error } = useFetch({}, () => requestGetReview(id));

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const goReviewPage = () => {
    history.push(`${PATH.REVIEW}`);
  };

  const goReviewEditPage = () => {
    history.push(`${PATH.REVIEW}/${id}/edit`);
  };

  const deleteReview = async () => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_REVIEW)) return;

    const response = await deleteReviewAsync(accessToken, id);

    if (!response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_REVIEW);

      return;
    }

    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_DELETE_REVIEW);
    goReviewPage();
  };

  if (error) {
    return <Error>{ERROR_MESSAGE.FAIL_TO_GET_REVIEW}</Error>;
  }

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
              onClick={goReviewPage}
            >
              <LeftArrowIcon width="18" height="18" stroke={FONT_COLOR.BLACK} />
              <div>목록 보기</div>
            </Button>
          </ButtonContainer>
          <TopContainer>
            <VaccinationInfo>
              <Label
                backgroundColor={VACCINATION_COLOR[review?.vaccinationType]}
                sizeType={LABEL_SIZE_TYPE.MEDIUM}
                fontColor={labelFontColor}
              >
                {VACCINATION[review?.vaccinationType]}
              </Label>
              <ShotVerified>{review?.writer?.shotVerified && '접종 확인'}</ShotVerified>
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
              {user.id === review?.writer?.id && (
                <UpdateButtonContainer>
                  <Button
                    backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                    color={FONT_COLOR.GRAY}
                    styles={buttonStyles}
                    onClick={goReviewEditPage}
                  >
                    수정
                  </Button>
                  <Button
                    backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                    color={FONT_COLOR.GRAY}
                    styles={buttonStyles}
                    onClick={deleteReview}
                  >
                    삭제
                  </Button>
                </UpdateButtonContainer>
              )}
            </InfoBottom>
          </TopContainer>
          <Content>{review?.content}</Content>
          <BottomContainer>
            <IconContainer>
              <LikeIcon width="26" height="26" stroke={FONT_COLOR.BLACK} fill={FONT_COLOR.BLACK} />
              <div>37</div>
            </IconContainer>
            <IconContainer>
              <CommentIcon width="20" height="20" stroke={FONT_COLOR.BLACK} />
              <div>12</div>
            </IconContainer>
          </BottomContainer>
          <Comment>
            <CommentCount>댓글 12</CommentCount>
            <CommentForm
              nickname={review.writer?.nickname}
              socialProfileUrl={review.writer?.socialProfileUrl}
            />
          </Comment>
        </FrameContent>
      </Frame>
    </Container>
  );
};

export default ReviewDetailPage;
