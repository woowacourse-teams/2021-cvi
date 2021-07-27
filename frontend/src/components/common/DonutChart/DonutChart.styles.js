import styled from '@emotion/styled';
import { THEME_COLOR } from '../../../constants';

const Container = styled.div`
  position: relative;
  /* display: inline-block; */
  border-radius: 50%;
  transition: 0.3s;

  width: ${({ radius }) => radius * 2}px;
  height: ${({ radius }) => radius * 2}px;
  background: ${({ currentNumber, color }) =>
    `conic-gradient(${color} 0% ${currentNumber}%, ${THEME_COLOR.WHITE} ${currentNumber}% 100%)`};
`;

const Span = styled.span`
  background-color: ${THEME_COLOR.WHITE};
  /* display: block; */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 50%;
  text-align: center;

  font-size: ${({ fontSize }) => fontSize && fontSize};
  width: ${({ radius }) => radius * 2}px;
  height: ${({ radius }) => radius * 2}px;
  line-height: ${({ radius }) => radius * 2}px;
`;
export { Container, Span };
