import styled from '@emotion/styled';
import { FONT_COLOR, LINE_LIMIT } from '../../constants';

const Container = styled.li`
  padding: 2.4rem 3.2rem;
  display: flex;
  flex-direction: column;
  cursor: pointer;

  @media screen and (max-width: 801px) {
    padding: 2rem;
  }
`;

const Content = styled.div`
  width: 100%;
  margin: 2.4rem 0;
  line-height: 1.5;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: ${LINE_LIMIT.REVIEW_ITEM};
  -webkit-box-orient: vertical;
  height: 7.2rem;
  overflow: hidden;
`;

const Writer = styled.div`
  font-size: 1.4rem;
`;

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
`;

const ViewCount = styled.div`
  margin-left: 0.4rem;
`;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
  color: ${FONT_COLOR.LIGHT_GRAY};
  font-size: 1.4rem;
  margin-top: 0.6rem;
`;

const InfoContainer = styled.div`
  display: flex;
  gap: 1.6rem;
`;

const IconContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export {
  Container,
  IconContainer,
  Content,
  Writer,
  ShotVerified,
  ViewCount,
  TopContainer,
  BottomContainer,
  InfoContainer,
};
