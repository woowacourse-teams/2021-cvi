import styled from '@emotion/styled';
import { LINE_LIMIT, FONT_COLOR } from '../../constants';

const Container = styled.div`
  padding: 1.2rem;
  width: 100%;
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
  justify-content: flex-end;
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
`;

export { Container, TopContainer, Content, BottomContainer, ShotVerified };
