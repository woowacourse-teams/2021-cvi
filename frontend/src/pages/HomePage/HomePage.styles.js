import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { THEME_COLOR } from '../../constants';

const Container = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 33%);
  padding: 2.5rem 8rem 6rem 8rem;
  column-gap: 2.4rem;
  row-gap: 6rem;

  & > *:nth-of-type(1) {
    grid-column-start: 1;
    grid-column-end: 4;
  }

  @media screen and (max-width: 1024px) {
    padding: 2.4rem 0 7.2rem 0;
    gap: 3rem;
    grid-template-columns: repeat(1, 1fr);
    & > *:nth-of-type(1) {
      grid-column-start: 1;
      grid-column-end: 2;
    }
  }
`;

const VaccinationSymptomColumn = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  @media screen and (max-width: 1024px) {
    gap: 2rem;
  }
`;

const A = styled.a`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 4.8rem;
  background-color: ${THEME_COLOR.WHITE};
  color: ${THEME_COLOR.PRIMARY};
  border-radius: 1.6rem;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
  cursor: pointer;

  &:hover {
    background-color: rgba(70, 159, 171, 0.08);
  }
`;

const buttonStyles = css`
  width: 100%;
  height: 4.8rem;
  border-radius: 1.6rem;
  background-color: ${THEME_COLOR.WHITE};
  border: transparent;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
`;

export { Container, VaccinationSymptomColumn, A, buttonStyles };
