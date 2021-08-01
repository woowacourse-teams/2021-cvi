import styled from '@emotion/styled';
import { PALETTE } from '../../../constants';

const BUTTON_SIZE_TYPE = Object.freeze({
  SMALL: 'SMALL',
  MEDIUM: 'MEDIUM',
  LARGE: 'LARGE',
});

const BUTTON_BACKGROUND_TYPE = Object.freeze({
  FILLED: 'FILLED',
  OUTLINE: 'OUTLINE',
  TEXT: 'TEXT',
});

const backgroundStyle = {
  FILLED: {
    color: PALETTE.WHITE,
  },
  OUTLINE: {
    backgroundColor: 'transparent',
  },
  TEXT: {
    backgroundColor: 'transparent',
    border: 'none',
  },
};

const hoverStyle = {
  FILLED: {
    backgroundColor: '#3e909c',
    border: '0.1rem solid #3e909c',
  },
  OUTLINE: {
    backgroundColor: 'rgba(70, 159, 171, 0.08)',
  },
};

const buttonStyle = {
  SMALL: {
    height: '2.4rem',
    fontSize: '1.2rem',
    borderRadius: '1.2rem',
    padding: '0 1rem',
  },
  MEDIUM: {
    height: '3.2rem',
    fontSize: '1.4rem',
    borderRadius: '1.6rem',
    padding: '0 1.2rem',
  },
  LARGE: {
    height: '4.0rem',
    fontSize: '1.6rem',
    borderRadius: '2.0rem',
    padding: '0 1.6rem',
  },
};

const Container = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  line-height: 1.5;
  width: fit-content;
  background-color: ${({ color }) => color};
  border: 0.1rem solid ${({ color }) => color};
  color: ${({ color }) => color};
  min-width: fit-content;

  ${({ backgroundType }) =>
    backgroundStyle[backgroundType] || backgroundStyle[BUTTON_BACKGROUND_TYPE.FILLED]};
  ${({ sizeType }) => buttonStyle[sizeType] || buttonStyle[BUTTON_SIZE_TYPE.MEDIUN]};

  &:hover {
    ${({ backgroundType }) => hoverStyle[backgroundType]};
  }

  & > *:not(:last-child) {
    margin-right: ${({ withIcon }) => withIcon && '1rem'};
  }

  ${({ styles }) => styles && styles}
  ${({ isSelected, selectedStyles }) => isSelected && selectedStyles}
`;

export { Container, BUTTON_SIZE_TYPE, BUTTON_BACKGROUND_TYPE };
