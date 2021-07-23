import styled from '@emotion/styled';
import { THEME_COLOR } from '../../../constants';

const Container = styled.div`
  display: flex;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  background-color: ${THEME_COLOR.BACKGROUND};
  position: relative;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2.5rem 8rem 6rem 8rem;
  flex: 1;
  overflow: auto;
  width: 100%;
`;

const TopContainer = styled.div`
  min-height: 3.6rem;
  display: flex;
  gap: 0.6rem;
  justify-content: flex-end;
`;

export { Container, MainContainer, TopContainer };
