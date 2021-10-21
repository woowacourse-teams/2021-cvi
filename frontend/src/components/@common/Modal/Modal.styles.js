import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../../constants';

const Container = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
  overflow-y: auto;

  ${({ backgroundColor }) => backgroundColor && `background-color: ${backgroundColor}`};
`;

const modalFrameStyles = css`
  min-width: 40rem;
  min-height: 40rem;
  position: relative;
  padding: 4rem;
  margin-top: 5rem;

  @media screen and (max-width: 480px) {
    padding: 4rem 2rem 2rem 2rem;
    width: 100%;
    height: 100%;
    min-width: auto;
    min-height: auto;
    margin-top: 0;
  }
`;

const CloseButton = styled.div`
  margin: 1.4rem;
  width: 3rem;
  position: absolute;
  right: 0.3rem;
  top: 0.3rem;
  cursor: pointer;
  background: none;
  border: none;

  svg {
    display: block;
    pointer-events: none;

    path {
      stroke: ${FONT_COLOR.GRAY};
      fill: transparent;
      stroke-linecap: round;
      stroke-width: 2;
      pointer-events: none;
    }
  }
`;

export { Container, modalFrameStyles, CloseButton };
