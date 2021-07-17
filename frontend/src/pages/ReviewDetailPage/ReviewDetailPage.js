import {
  Container,
  FrameContent,
  ButtonContainer,
  Info,
  ReviewInfo,
  ShotVerified,
  WriterContainer,
  Writer,
  CreatedAt,
  Content,
  ViewCount,
  Error,
} from './ReviewDetailPage.styles';
import Frame from '../../components/Frame/Frame';
import { useHistory, useParams } from 'react-router-dom';
import { useFetch } from '../../hooks';
import { requestGetReview } from '../../requests';
import Label from '../../components/Label/Label';
import { LABEL_SIZE_TYPE } from '../../components/Label/Label.styles';
import { ERROR_MESSAGE, FONT_COLOR, PATH, VACCINATION, VACCINATION_COLOR } from '../../constants';
import Button from '../../components/Button/Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../../components/Button/Button.styles';
import LeftArrowIcon from '../../assets/icons/left-arrow.svg';
import Avatar from '../../components/Avatar/Avatar';

const ReviewDetailPage = () => {
  const history = useHistory();
  const { id } = useParams();

  const { response: review, error } = useFetch({}, () => requestGetReview(id));

  const labelFontColor =
    review?.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const goBack = () => {
    history.goBack();
  };

  if (error) {
    return <Error>{ERROR_MESSAGE.FAIL_TO_GET_REVIEW}</Error>;
  }

  return (
    <Container>
      <Frame width="100%">
        <FrameContent>
          <ButtonContainer>
            <Button
              sizeType={BUTTON_SIZE_TYPE.LARGE}
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              color={FONT_COLOR.BLACK}
              withIcon={true}
              onClick={goBack}
            >
              <LeftArrowIcon width="24" height="24" stroke={FONT_COLOR.BLACK} />
              <div>뒤로 가기</div>
            </Button>
          </ButtonContainer>
          <Info>
            <ReviewInfo>
              <Label
                backgroundColor={VACCINATION_COLOR[review?.vaccinationType]}
                sizeType={LABEL_SIZE_TYPE.MEDIUM}
                fontColor={labelFontColor}
              >
                {VACCINATION[review?.vaccinationType]}
              </Label>
              <ShotVerified>{review?.writer?.shotVerified && '접종 확인'}</ShotVerified>
            </ReviewInfo>
            <WriterContainer>
              <Avatar />
              <Writer>
                {review?.writer?.nickname} · {review?.writer?.ageRange?.meaning}
              </Writer>
            </WriterContainer>
            <CreatedAt>{review?.createdAt}</CreatedAt>
          </Info>
          <Content>{review?.content}</Content>
          <ViewCount>{review?.viewCount}</ViewCount>
        </FrameContent>
      </Frame>
    </Container>
  );
};

export default ReviewDetailPage;
