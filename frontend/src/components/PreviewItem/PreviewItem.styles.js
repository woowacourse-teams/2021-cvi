import styled from '@emotion/styled';
import { LINE_LIMIT, FONT_COLOR } from '../../constants';

const Container = styled.div`
  padding: 1.6rem 1.6rem 1rem 1.6rem;
  width: 100%;
  cursor: pointer;

  @media screen and (max-width: 801px) {
    padding: 2rem;
  }
`;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;
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

export {
  Container,
  TopContainer,
  Content,
  BottomContainer,
  IconContainer,
  IconItem,
  ViewCount,
  CreatedAt,
};
