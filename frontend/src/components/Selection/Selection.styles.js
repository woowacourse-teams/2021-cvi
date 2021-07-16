import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { PALETTE, VACCINATION_COLOR } from '../../constants';

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

  & > *:not(:last-child) {
    margin-right: 1.6rem;
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
  background-color: ${PALETTE.RED300};
  color: ${PALETTE.WHITE};
`;

export { Container, buttonSelectedStyles, defaultButtonStyles };
