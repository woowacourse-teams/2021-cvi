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
  CommentList,
  CommentFormContainer,
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
import { CommentForm, CommentItem } from '../../components';

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

  const commentList = [
    {
      id: 1,
      writer: {
        id: 1,
        nickname: 'test_user1',
        ageRange: {
          meaning: '10대',
          minAge: 10,
          maxAge: 20,
        },
        shotVerified: false,
        accessToken: null,
        socialProvider: 'NAVER',
        socialId: '{Unique ID received from social provider}',
        socialProfileUrl: user.socialProfileUrl,
      },
      content: '아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳',
      createdAt: '2021-07-29T19:23:38.959',
    },
    {
      id: 2,
      writer: {
        id: 2,
        nickname: 'test_user2',
        ageRange: {
          meaning: '20대',
          minAge: 20,
          maxAge: 30,
        },
        shotVerified: true,
        accessToken: null,
        socialProvider: 'KAKAO',
        socialId: '{Unique ID received from social provider}',
        socialProfileUrl: user.socialProfileUrl,
      },
      content:
        '아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳아니 이거 글이 너무 ',
      createdAt: '2021-07-29T19:23:38.959',
    },
    {
      id: 3,
      writer: {
        id: 1,
        socialProfileUrl: user.socialProfileUrl,
        nickname: 'test_user3',
        ageRange: {
          meaning: '20대',
        },
        shotVerified: false,
      },
      content: '아니 이거 글이 너무 좋네요!!하하하하하하하하하하하하하하핳',
      createdAt: '2021-07-26T14:36:37.929',
    },
  ];

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
            <CommentCount>댓글 {review?.comments?.length}</CommentCount>
            <CommentFormContainer>
              <CommentForm
                accessToken={accessToken}
                nickname={user.nickname}
                socialProfileUrl={user.socialProfileUrl}
              />
            </CommentFormContainer>
            <CommentList>
              {review?.comments?.map((comment) => (
                <CommentItem key={comment.id} comment={comment} reviewId={id} />
              ))}
            </CommentList>
          </Comment>
        </FrameContent>
      </Frame>
    </Container>
  );
};

export default ReviewDetailPage;
