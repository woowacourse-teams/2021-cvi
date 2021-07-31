import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../../constants';

const Container = styled.div`
  display: flex;
  height: 5.2rem;
  width: 100%;
  border-bottom: 0.2rem solid ${PALETTE.NAVY100};
  justify-content: flex-start;
  align-items: center;
  line-height: 1.5;
  padding: 0 3.2rem;

  @media screen and (max-width: 801px) {
    padding: 0 1rem;
    overflow-x: auto;
  }
`;

const buttonStyles = css`
  height: 100%;
  border-radius: 0;

  &:hover {
    background-color: rgba(0, 0, 0, 0.04);
  }
`;

const selectedButtonStyles = css`
  border-bottom: 2px solid ${FONT_COLOR.BLACK};
  color: ${PALETTE.GRAY900};
  padding-bottom: 0.1rem;
  box-sizing: content-box;
`;

export { Container, buttonStyles, selectedButtonStyles };
