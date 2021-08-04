import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, LINE_LIMIT } from '../../constants';

const Container = styled.div`
  cursor: pointer;
`;

const ReviewContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  border: 0.1rem transparent ${FONT_COLOR.LIGHT_GRAY};
  border-radius: 1.6rem;
  padding: 1.8rem;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
`;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const CreatedAt = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const CommentContent = styled.div`
  margin: 0.6rem 0 2.4rem 0;
`;

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
`;

const ReviewContent = styled.div`
  font-size: 1.4rem;
  margin: 2rem 0;
  line-height: 1.5;
  min-height: 1.5rem;
  white-space: normal;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: ${LINE_LIMIT.MY_COMMENT_ITEM};
  -webkit-box-orient: vertical;
`;

const Writer = styled.div`
  font-size: 1.4rem;
`;

const frameStyle = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 2.4rem;
`;

export {
  Container,
  ReviewContainer,
  TopContainer,
  CreatedAt,
  CommentContent,
  ShotVerified,
  ReviewContent,
  Writer,
  frameStyle,
};
