import { useSelector } from 'react-redux';
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
  UpdateButtonContainer,
  CreatedAt,
  Content,
  ViewCount,
  Error,
} from './ReviewDetailPage.styles';
import Frame from '../../components/Frame/Frame';
import { useHistory, useParams } from 'react-router-dom';
import { useFetch } from '../../hooks';
import { requestDeleteReview, requestGetReview } from '../../requests';
import Label from '../../components/Label/Label';
import { LABEL_SIZE_TYPE } from '../../components/Label/Label.styles';
import {
  CONFIRM_MESSAGE,
  ERROR_MESSAGE,
  FONT_COLOR,
  PATH,
  TO_DATE_TYPE,
  VACCINATION,
  VACCINATION_COLOR,
} from '../../constants';
import Button from '../../components/Button/Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';
import LeftArrowIcon from '../../assets/icons/left-arrow.svg';
import Avatar from '../../components/Avatar/Avatar';
import toDate from '../../utils/toDate';
import ClockIcon from '../../assets/icons/clock.svg';
import EyeIcon from '../../assets/icons/eye.svg';

const ReviewDetailPage = () => {
  const history = useHistory();
  const { id } = useParams();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const { response: review, error } = useFetch({}, () => requestGetReview(id));

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const goBack = () => {
    history.goBack();
  };

  const goReviewPage = () => {
    history.push(`${PATH.REVIEW}`);
  };

  const editReview = () => {};

  const deleteReview = async () => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_REVIEW)) return;

    try {
      const response = await requestDeleteReview(accessToken, id);

      if (!response.ok) {
        throw new Error();
      }

      goReviewPage();
    } catch (error) {
      console.error(error);
    }
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
              onClick={goBack}
            >
              <LeftArrowIcon width="18" height="18" stroke={FONT_COLOR.BLACK} />
              <div>뒤로 가기</div>
            </Button>
          </ButtonContainer>
          <Info>
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
              {user.id === review?.writer?.id && (
                <UpdateButtonContainer>
                  <Button
                    backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                    color={FONT_COLOR.GRAY}
                    onClick={editReview}
                  >
                    수정
                  </Button>
                  <Button
                    backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                    color={FONT_COLOR.GRAY}
                    onClick={deleteReview}
                  >
                    삭제
                  </Button>
                </UpdateButtonContainer>
              )}
            </InfoBottom>
          </Info>
          <Content>{review?.content}</Content>
        </FrameContent>
      </Frame>
    </Container>
  );
};

export default ReviewDetailPage;
