import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
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
  buttonStyles,
  Comment,
  IconContainer,
  BottomContainer,
  CommentCount,
  CommentFormContainer,
} from './ReviewDetailPage.styles';
import { useHistory, useParams } from 'react-router-dom';
import { LABEL_SIZE_TYPE } from '../../components/common/Label/Label.styles';
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
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/common/Button/Button.styles';
import { toDate } from '../../utils';
import { ClockIcon, EyeIcon, LeftArrowIcon, CommentIcon } from '../../assets/icons';
import { deleteReviewAsync, getReviewAsync } from '../../service';
import { Avatar, Button, Frame, Label } from '../../components/common';
import { CommentForm, CommentItem } from '../../components';
import { useLike, useSnackBar } from '../../hooks';

// TODO: Comment 컴포넌트 분리
const ReviewDetailPage = () => {
  const history = useHistory();
  const { id } = useParams();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [review, setReview] = useState({});

  const getReview = async () => {
    const response = await getReviewAsync(accessToken, id);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert('failure - getReviewAsync');

      return;
    }

    setReview(response.data);
  };

  const { onClickLike, ButtonLike } = useLike(accessToken, review.hasLiked, id, getReview);
  const { isSnackBarOpen, openSnackBar, SnackBar } = useSnackBar();

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

    openSnackBar();
    goReviewPage();
  };

  useEffect(() => {
    getReview();
  }, []);

  return (
    <>
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
                <ButtonLike
                  iconWidth="24"
                  iconHeight="24"
                  color={FONT_COLOR.BLACK}
                  likeCountSize="1.6rem"
                  hasLiked={review?.hasLiked}
                  likeCount={review?.likeCount}
                  onClickLike={onClickLike}
                />
              </IconContainer>
              <IconContainer>
                <CommentIcon width="20" height="20" stroke={FONT_COLOR.BLACK} />
                <div>{review?.comments?.length}</div>
              </IconContainer>
            </BottomContainer>
            <Comment>
              <CommentCount>댓글 {review?.comments?.length}</CommentCount>
              <CommentFormContainer>
                <CommentForm
                  accessToken={accessToken}
                  reviewId={id}
                  nickname={user.nickname}
                  socialProfileUrl={user.socialProfileUrl}
                  getReview={getReview}
                />
              </CommentFormContainer>
              {review?.comments?.map((comment) => (
                <CommentItem
                  key={comment.id}
                  accessToken={accessToken}
                  userId={user.id}
                  reviewId={id}
                  comment={comment}
                  getReview={getReview}
                />
              ))}
            </Comment>
          </FrameContent>
        </Frame>
      </Container>
      {isSnackBarOpen && <SnackBar>{SNACKBAR_MESSAGE.SUCCESS_TO_DELETE_REVIEW}</SnackBar>}
    </>
  );
};

export default ReviewDetailPage;
