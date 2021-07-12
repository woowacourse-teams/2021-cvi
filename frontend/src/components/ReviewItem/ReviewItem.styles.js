import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  padding: 2.4rem 3.2rem;
  display: flex;
  flex-direction: column;
`;

const Content = styled.div`
  width: 100%;
  margin: 2.4rem 0;
  line-height: 1.5;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const Writer = styled.div`
  font-size: 1.4rem;
`;

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
`;

const ViewCount = styled.div``;

const Date = styled.div``;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
  color: ${FONT_COLOR.LIGHT_GRAY};
  font-size: 1.4rem;
  margin-top: 0.4rem;
`;

export { Container, Content, Writer, ShotVerified, ViewCount, Date, TopContainer, BottomContainer };
