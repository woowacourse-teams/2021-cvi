import styled from '@emotion/styled';
import { LINE_LIMIT, FONT_COLOR } from '../../constants';

const Container = styled.div`
  padding: 2rem;
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
  margin: 2rem 0;
  line-height: 1.5;
  min-height: 7.2rem;
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

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
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
`;

export {
  Container,
  TopContainer,
  Content,
  BottomContainer,
  ShotVerified,
  IconContainer,
  IconItem,
  ViewCount,
  CreatedAt,
};
