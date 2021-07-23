import styled from '@emotion/styled';
import { THEME_COLOR } from '../../../constants';

const Container = styled.div`
  display: flex;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  background-color: ${THEME_COLOR.BACKGROUND};
  position: relative;
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  overflow: auto;
  width: 100%;
`;

const TopContainer = styled.div`
  display: flex;
  gap: 0.6rem;
  justify-content: flex-end;
  padding: 2rem 8rem 0 0;
`;

export { Container, MainContainer, TopContainer };
