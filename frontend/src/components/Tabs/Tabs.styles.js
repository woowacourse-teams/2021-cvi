import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Container = styled.button`
  display: flex;
  height: 4.5rem;
  width: 100%;
  border-bottom: 0.15rem solid ${PALETTE.NAVY100};
  justify-content: center;
  align-items: center;
  line-height: 1.5;
`;

const buttonStyles = css`
  height: 100%;
  border-radius: 0;

  &:hover {
    background-color: rgba(0, 0, 0, 0.04);
  }
`;

const selectedButtonStyles = css`
  border-bottom: 2px solid ${PALETTE.GRAY900};
  color: ${PALETTE.GRAY900};
`;

export { Container, buttonStyles, selectedButtonStyles };
