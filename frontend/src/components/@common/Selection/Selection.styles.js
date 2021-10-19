import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { PALETTE, VACCINATION_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  line-height: 1.5;
  width: fit-content;
  border: 0.15rem solid ${PALETTE.NAVY300};
  height: 5.2rem;
  border-radius: 2.6rem;
  padding: 0 0.6rem;
  background-color: ${THEME_COLOR.WHITE};
  overflow-x: auto;

  & > *:not(:last-child) {
    margin-right: 1.6rem;
  }

  @media screen and (max-width: 801px) {
    display: grid;
    border-radius: 1.6rem;
    grid-template-columns: repeat(2, 1fr);
    padding: 1rem;
    width: 100%;
    min-height: 10rem;
  }
`;

const buttonSelectedStyles = {
  모더나: css`
    background-color: ${VACCINATION_COLOR.MODERNA};
    color: ${PALETTE.WHITE};
  `,
  화이자: css`
    background-color: ${VACCINATION_COLOR.PFIZER};
    color: ${PALETTE.WHITE};
  `,
  아스트라제네카: css`
    background-color: ${VACCINATION_COLOR.ASTRAZENECA};
    color: ${PALETTE.GRAY700};
  `,
  얀센: css`
    background-color: ${VACCINATION_COLOR.JANSSEN};
    color: ${PALETTE.WHITE};
  `,
};

const defaultButtonStyles = css`
  background-color: ${THEME_COLOR.PRIMARY};
  color: ${PALETTE.WHITE};
`;

const buttonStyles = css`
  @media screen and (max-width: 801px) {
    border-radius: 1.2rem;
    width: 100%;
  }
`;

export { Container, buttonSelectedStyles, defaultButtonStyles, buttonStyles };
