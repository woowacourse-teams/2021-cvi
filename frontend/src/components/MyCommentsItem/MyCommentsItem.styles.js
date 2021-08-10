import styled from '@emotion/styled';
import { FONT_COLOR, LINE_LIMIT, PALETTE } from '../../constants';

const Container = styled.div`
  width: 100%;
  cursor: pointer;
`;

const Content = styled.div`
  padding: 2.4rem 3.2rem;
  border-bottom: 0.15rem solid ${PALETTE.NAVY100};
`;

const ReviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  border-radius: 1.6rem;
  padding: 1.8rem;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
`;

const CreatedAt = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const CommentContent = styled.div`
  margin: 0.6rem 0 2.4rem 0;
`;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;
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

export {
  Container,
  Content,
  CreatedAt,
  CommentContent,
  ReviewContainer,
  TopContainer,
  ShotVerified,
  ReviewContent,
  Writer,
};
