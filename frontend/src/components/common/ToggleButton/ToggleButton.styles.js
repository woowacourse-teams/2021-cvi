import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  width: 60%;
  max-width: 60rem;
  min-height: 3.6rem;
  background-color: #f0f0f0;
  cursor: pointer;
  user-select: none;
  border-radius: 1.6rem;
  position: relative;
  display: flex;
`;

const ToggleItem = styled.div`
  width: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.4rem;
  color: ${({ isSelected }) => (isSelected ? FONT_COLOR.BLACK : FONT_COLOR.GRAY)};
  gap: 0.4rem;
`;

const DialogButton = styled.div`
  width: 50%;
  height: 3.6rem;
  cursor: pointer;
  background-color: ${THEME_COLOR.WHITE};
  padding: 0.8rem 1.2rem;
  border-radius: 1.6rem;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 0px 8px 4px rgba(0, 0, 0, 0.08);
  position: absolute;
  left: 50%;
  transition: all 0.3s ease;
  font-size: 1.4rem;
  font-weight: 600;
  z-index: 1;

  ${({ selected }) => !selected && `background-color: ${THEME_COLOR.WHITE}; left: 0`}
`;

export { Container, DialogButton, ToggleItem };
