import styled from '@emotion/styled';
import { FONT_COLOR } from '../../../constants';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.4rem;
  width: 30rem;
  height: 5rem;
  border-radius: 0.4rem;
  background-color: ${FONT_COLOR.BLACK};
  color: ${FONT_COLOR.WHITE};
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
  z-index: 100;
  position: absolute;
  bottom: 1.6rem;
  left: 50%;
  transform: translateX(-50%);
`;

export { Container };
