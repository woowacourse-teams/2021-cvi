import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 9fr;
  padding: 1rem 1rem 1rem 3rem;
  position: relative;

  & > * {
    align-items: center;
  }

  @media screen and (max-width: 801px) {
    grid-template-columns: 2fr 8fr;
    margin: 1rem 1.4rem;
    padding: 0;
    row-gap: 0.8rem;
  }
`;

const Type = styled.div`
  font-weight: 600;
  display: flex;
  align-items: center;

  @media screen and (max-width: 801px) {
    align-items: flex-start;
    padding-top: 0.4rem;
  }
`;

const closeOptionListButtonStyles = css`
  display: flex;
  justify-content: flex-end;
  height: 5.2rem;
  padding-bottom: 1.2rem;
  padding-right: 0.4rem;
  position: absolute;
  top: 1.2rem;
  right: 2rem;

  @media screen and (max-width: 801px) {
    top: 0;
    right: 1rem;
  }
`;

export { Container, Type, closeOptionListButtonStyles };
