import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 8fr 1fr;
  grid-template-rows: repeat(3, 1fr);
  padding: 1rem 1rem 1rem 3rem;

  & > * {
    display: flex;
    align-items: center;
  }
  & > button {
    grid-column-start: 3;
    grid-column-end: 4;
    grid-row-start: 1;
    grid-row-end: 4;
    justify-self: end;
  }
`;

const closeOptionListButtonStyles = css`
  display: flex;
  justify-content: flex-end;
  height: 5.2rem;
  padding-bottom: 1.2rem;
  padding-right: 0.4rem;
`;

export { Container, closeOptionListButtonStyles };
