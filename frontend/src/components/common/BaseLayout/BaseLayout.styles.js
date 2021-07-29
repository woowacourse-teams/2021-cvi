import { css } from '@emotion/react';
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
  overflow-y: auto;
  width: 100%;
`;

const TopContainer = styled.div`
  display: flex;
  gap: 0.6rem;
  justify-content: flex-end;
  padding: 2rem 8rem 0 0;
  min-height: 5.6rem;
`;

const avatarStyles = css`
  &:hover {
    cursor: pointer;
  }
`;

export { Container, MainContainer, TopContainer, avatarStyles };
