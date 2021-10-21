import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  Container,
  FrameContent,
  ButtonContainer,
  TopContainer,
  VaccinationInfo,
  ReviewInfo,
  WriterInfo,
  Writer,
  InfoBottom,
  UpdateButtonContainer,
  CreatedAt,
  Content,
  ViewCount,
  buttonStyles,
  IconContainer,
  BottomContainer,
  ImageContainer,
} from './ReviewDetailPage.styles';
import { useParams } from 'react-router-dom';
import { LABEL_SIZE_TYPE } from '../../components/@common/Label/Label.styles';
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  FONT_COLOR,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
  THEME_COLOR,
  TO_DATE_TYPE,
  VACCINATION,
  VACCINATION_COLOR,
} from '../../constants';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/@common/Button/Button.styles';
import { toDate } from '../../utils';
import { ClockIcon, EyeIcon, LeftArrowIcon, CommentIcon } from '../../assets/icons';
import { Avatar, Button, Frame, Label } from '../../components/@common';
import { Comment, ImageModal, ReviewImage } from '../../components';
import { useLike, useSnackbar, useLoading, useMovePage } from '../../hooks';
import customRequest from '../../service/customRequest';
import { fetchDeleteReview, fetchGetReview } from '../../service/fetch';

const ReviewDetailPage = () => {
  const { id } = useParams();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [review, setReview] = useState({});
  const [commentCount, setCommentCount] = useState(0);
  const [isOpenImageModal, setIsOpenImageModal] = useState(false);
  const [openedImageSrc, setOpenedImageSrc] = useState('');

  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const { goReviewPage, goReviewEditPage } = useMovePage();
  const getReview = async () => {
    const response = await customRequest(() => fetchGetReview(accessToken, id));

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert('failure - getReviewAsync');

      return;
    }

    setReview(response.data);
    hideLoading();
  };

  const { onClickLike, ButtonLike, updatedHasLiked, updatedLikeCount } = useLike(
    accessToken,
    review?.hasLiked,
    review?.likeCount,
    id,
  );
  const { openSnackbar } = useSnackbar();

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const deleteReview = async () => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_REVIEW)) return;

    const response = await customRequest(() => fetchDeleteReview(accessToken, id));

    if (!response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_REVIEW);

      return;
    }

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_DELETE_REVIEW);
    goReviewPage();
  };

  const clickImage = (src) => {
    setOpenedImageSrc(src);
    setIsOpenImageModal(true);
  };

  useEffect(() => {
    showLoading();
    getReview();
  }, [accessToken]);

  useEffect(() => {
    setCommentCount(review?.commentCount);
  }, [review]);

  return (
    <>
      <Container>
        <Frame width="100%" showShadow={true}>
          <FrameContent>
            {isLoading ? (
              <Loading isLoading={isLoading} backgroundColor={THEME_COLOR.WHITE} />
            ) : (
              <>
                <ButtonContainer>
                  <Button
                    sizeType={BUTTON_SIZE_TYPE.LARGE}
                    backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                    color={FONT_COLOR.BLACK}
                    withIcon={true}
                    onClick={() => goReviewPage()}
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
                          onClick={() => goReviewEditPage(id)}
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
                <ImageContainer>
                  {review?.images?.map((image, index) => (
                    <ReviewImage
                      key={`${review.id}-image-${index}`}
                      alt={`${review.id}-image-${index}`}
                      src={image}
                      showDetailIcon={true}
                      onClick={() => clickImage(image)}
                    />
                  ))}
                </ImageContainer>
                <BottomContainer>
                  <IconContainer>
                    <ButtonLike
                      iconWidth="24"
                      iconHeight="24"
                      color={FONT_COLOR.BLACK}
                      likeCountSize="1.6rem"
                      hasLiked={updatedHasLiked ?? false}
                      likeCount={updatedLikeCount ?? 0}
                      onClickLike={onClickLike}
                    />
                  </IconContainer>
                  <IconContainer>
                    <CommentIcon width="20" height="20" stroke={FONT_COLOR.BLACK} />
                    <div>{commentCount}</div>
                  </IconContainer>
                </BottomContainer>
                <Comment
                  accessToken={accessToken}
                  user={user}
                  commentCount={commentCount ?? 0}
                  setCommentCount={setCommentCount}
                  reviewId={id}
                  getReview={getReview}
                />
              </>
            )}
          </FrameContent>
        </Frame>
      </Container>
      {isOpenImageModal && (
        <ImageModal src={openedImageSrc} onClickClose={() => setIsOpenImageModal(false)} />
      )}
    </>
  );
};

export default ReviewDetailPage;
