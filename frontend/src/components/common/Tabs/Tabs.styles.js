import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const SELECTED_TAB_STYLE_TYPE = Object.freeze({
  UNDERLINE: 'UNDERLINE',
  LEFT_CIRCLE: 'LEFT_CIRCLE',
});

const Container = styled.div`
  display: flex;
  height: 5.2rem;
  width: 100%;
  justify-content: flex-start;
  align-items: center;
  line-height: 1.5;
  padding: 0 3.2rem;

  @media screen and (max-width: 801px) {
    padding: 0 1rem;
    height: fit-content;
    display: grid;
    grid-template-columns: 1fr 1fr;
  }
`;

const buttonStyles = css`
  height: 100%;
  border-radius: 0;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    left: 0.5rem;
    width: 0.6rem;
    height: 0.6rem;
    border-radius: 50%;
    background-color: ${FONT_COLOR.BLUE_GRAY};
    font-size: 1.6rem;
    border: 0.1rem soid ${FONT_COLOR.LIGHT_GRAY};
  }

  @media screen and (max-width: 801px) {
    padding: 0.4rem 0 0.4rem 1.6rem;
  }
`;

const selectedButtonStyles = {
  [SELECTED_TAB_STYLE_TYPE.UNDERLINE]: css`
    border-bottom: 2px solid ${FONT_COLOR.BLACK};
    color: ${FONT_COLOR.BLACK};
    padding-bottom: 0.1rem;
    box-sizing: content-box;
  `,
  [SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE]: css`
    color: ${FONT_COLOR.BLACK};
    position: relative;
    height: 100%;

    &::before {
      content: '';
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      left: 0.5rem;
      width: 0.6rem;
      height: 0.6rem;
      border-radius: 50%;
      background-color: ${THEME_COLOR.PRIMARY};
    }
  `,
};

export { Container, buttonStyles, selectedButtonStyles, SELECTED_TAB_STYLE_TYPE };
