import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { THEME_COLOR } from '../../../constants';

const LikeCount = styled.div`
  margin-left: 0.4rem;
  font-size: ${({ likeCountSize }) => likeCountSize && likeCountSize};

  color: ${({ hasLiked, color }) => (hasLiked ? THEME_COLOR.PRIMARY : color)};
`;
const likeButtonStyles = css`
  padding: 0;
`;

export { LikeCount, likeButtonStyles };
