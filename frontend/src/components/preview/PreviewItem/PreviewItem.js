import PropTypes from 'prop-types';
import {
  Container,
  Content,
  BottomContainer,
  IconContainer,
  IconItem,
  ViewCount,
  CreatedAt,
  ContentContainer,
  PreviewImageContainer,
  PreviewImage,
  MoreImageCount,
} from './PreviewItem.styles';
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR, TO_DATE_TYPE } from '../../../constants';
import { toDate } from '../../../utils';
import { Label } from '../../@common';
import { LABEL_SIZE_TYPE } from '../../@common/Label/Label.styles';
import { CommentIcon, EyeIcon } from '../../../assets/icons';
import { useLike } from '../../../hooks';

const PreviewItem = ({ review, accessToken, onClick, withShadow, withImage }) => {
  const {
    id,
    content,
    vaccinationType,
    createdAt,
    commentCount,
    hasLiked,
    likeCount,
    viewCount,
    images,
  } = review;
  const labelFontColor = vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const { onClickLike, ButtonLike, updatedHasLiked, updatedLikeCount } = useLike(
    accessToken,
    hasLiked,
    likeCount,
    id,
  );

  return (
    <Container withShadow={withShadow} onClick={onClick}>
      <Label
        sizeType={LABEL_SIZE_TYPE.SMALL}
        backgroundColor={VACCINATION_COLOR[vaccinationType]}
        fontColor={labelFontColor}
      >
        {VACCINATION[vaccinationType]}
      </Label>
      <ContentContainer>
        <Content>{content}</Content>
        {withImage && !!images?.length && (
          <PreviewImageContainer>
            <PreviewImage src={images[0]} alt={`${id}-preview`} />
            {images?.length > 1 && <MoreImageCount>+ {images?.length - 1}</MoreImageCount>}
          </PreviewImageContainer>
        )}
      </ContentContainer>
      <BottomContainer>
        <IconContainer>
          <IconItem>
            <EyeIcon width="18" height="18" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{viewCount}</ViewCount>
          </IconItem>
          <IconItem>
            <ButtonLike
              hasLiked={updatedHasLiked}
              likeCount={updatedLikeCount}
              onClickLike={onClickLike}
            />
          </IconItem>
          <IconItem>
            <CommentIcon width="16" height="16" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{commentCount}</ViewCount>
          </IconItem>
        </IconContainer>
        <CreatedAt>{toDate(TO_DATE_TYPE.TIME, createdAt)}</CreatedAt>
      </BottomContainer>
    </Container>
  );
};

PreviewItem.propTypes = {
  review: PropTypes.shape({
    id: PropTypes.number.isRequired,
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
    commentCount: PropTypes.number.isRequired,
    likeCount: PropTypes.number.isRequired,
    viewCount: PropTypes.number.isRequired,
    hasLiked: PropTypes.bool.isRequired,
    images: PropTypes.array.isRequired,
  }).isRequired,
  accessToken: PropTypes.string,
  onClick: PropTypes.func,
  withShadow: PropTypes.bool,
  withImage: PropTypes.bool,
};

PreviewItem.defaultProps = {
  accessToken: '',
  onClick: () => {},
  withShadow: false,
  withImage: false,
};

export default PreviewItem;
