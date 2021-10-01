import styled from '@emotion/styled';
import { LINE_LIMIT, FONT_COLOR } from '../../../constants';

const Container = styled.div`
  padding: 1.6rem 1.6rem 1rem 1.6rem;
  width: 100%;
  cursor: pointer;

  ${({ withShadow }) => withShadow && `box-shadow: 0 0 4px 1px rgba(60, 64, 67, 0.1)`};

  @media screen and (max-width: 801px) {
    padding: 2rem;
  }
`;

const ContentContainer = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 2rem;
`;

const Content = styled.div`
  margin: 1.6rem 0 1rem 0;
  line-height: 1.5;
  min-height: 2.4rem;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: ${LINE_LIMIT.PREVIEW_ITEM};
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const IconContainer = styled.div`
  display: flex;
  gap: 1.6rem;
`;

const IconItem = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const ViewCount = styled.div`
  margin-left: 0.4rem;
`;

const CreatedAt = styled.div`
  display: flex;
  align-items: center;
  white-space: nowrap;
`;
const PreviewImage = styled.img`
  width: 8rem;
  height: 8rem;
`;

const PreviewImageContainer = styled.div`
  width: 8rem;
  height: 8rem;
  position: relative;
  margin-bottom: 1.6rem;
`;

const MoreImageCount = styled.div`
  width: 8rem;
  height: 8rem;
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
  ContentContainer,
  Content,
  BottomContainer,
  IconContainer,
  IconItem,
  ViewCount,
  CreatedAt,
  PreviewImageContainer,
  PreviewImage,
  MoreImageCount,
};
