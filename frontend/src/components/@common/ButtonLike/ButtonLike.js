import PropTypes from 'prop-types';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';
import { Button } from '..';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';
import { LikeIcon, LikeTrueIcon } from '../../../assets/icons';
import { LikeCount, likeButtonStyles } from './ButtonLike.styles';

const ButtonLike = ({
  iconWidth,
  iconHeight,
  color,
  likeCountSize,
  hasLiked,
  likeCount,
  onClickLike,
}) => (
  <Button
    backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
    color={color}
    styles={likeButtonStyles}
    aria-label="like-button"
    onClick={onClickLike}
  >
    {hasLiked ? (
      <LikeTrueIcon
        width={iconWidth}
        height={iconHeight}
        stroke={THEME_COLOR.PRIMARY}
        fill={THEME_COLOR.PRIMARY}
      />
    ) : (
      <LikeIcon width={iconWidth} stroke={color} fill={color} />
    )}
    <LikeCount hasLiked={hasLiked} likeCountSize={likeCountSize} color={color}>
      {likeCount}
    </LikeCount>
  </Button>
);

ButtonLike.propTypes = {
  hasLiked: PropTypes.bool.isRequired,
  likeCount: PropTypes.number.isRequired,
  color: PropTypes.string,
  iconHeight: PropTypes.string,
  iconWidth: PropTypes.string,
  likeCountSize: PropTypes.string,
  onClickLike: PropTypes.func,
};

ButtonLike.defaultProps = {
  color: FONT_COLOR.LIGHT_GRAY,
  iconHeight: '18',
  iconWidth: '18',
  likeCountSize: '1.4rem',
  onClickLike: () => {},
};

export default ButtonLike;
