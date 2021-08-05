import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  width: 60%;
  max-width: 60rem;
  height: 3.6rem;
  background-color: #f0f0f0;
  cursor: pointer;
  user-select: none;
  border-radius: 1.6rem;
  position: relative;
`;

const DialogButton = styled.div`
  width: 50%;
  height: 3.6rem;
  cursor: pointer;
  background-color: ${THEME_COLOR.PRIMARY};
  color: ${FONT_COLOR.WHITE};
  padding: 0.8rem 1.2rem;
  border-radius: 1.6rem;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.25);
  position: absolute;
  left: 50%;
  transition: all 0.3s ease;

  ${({ selected }) => !selected && `background-color: ${THEME_COLOR.PRIMARY}; left: 0`}
`;

export { Container, DialogButton };
