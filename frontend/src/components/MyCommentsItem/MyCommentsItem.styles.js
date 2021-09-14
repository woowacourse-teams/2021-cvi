import styled from '@emotion/styled';
import { FONT_COLOR, LINE_LIMIT } from '../../constants';

const Container = styled.div`
  width: 100%;
  cursor: pointer;
`;

const Content = styled.div`
  padding: 2.4rem 3.2rem;

  @media screen and (max-width: 1024px) {
    padding: 2.4rem 2rem;
  }
`;

const ReviewContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: space-between;
  width: 100%;
  border-radius: 1.6rem;
  padding: 1.8rem;
  box-shadow: 0 0 4px 1px rgba(60, 64, 67, 0.1);
`;

const CreatedAt = styled.div`
  white-space: nowrap;
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const CommentContent = styled.div`
  margin: 0.6rem 0 2.4rem 0;
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

const PreviewImage = styled.img`
  width: 12rem;
  height: 12rem;
`;

const PreviewImageContainer = styled.div`
  width: 12rem;
  height: 12rem;
  position: relative;
`;

const MoreImageCount = styled.div`
  width: 12rem;
  height: 12rem;
  position: absolute;
  top: 0;
  left: 0;
  font-size: 2.8rem;
  color: ${FONT_COLOR.WHITE};
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export {
  Container,
  Content,
  CreatedAt,
  CommentContent,
  ReviewContainer,
  ReviewContent,
  Writer,
  PreviewImage,
  PreviewImageContainer,
  MoreImageCount,
};
