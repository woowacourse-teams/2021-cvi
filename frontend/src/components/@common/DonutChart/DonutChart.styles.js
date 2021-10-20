import styled from '@emotion/styled';

const AnimatedCircle = styled.circle`
  -webkit-animation: circle-fill-animation 3s;
  -moz-animation: circle-fill-animation 3s;
  animation: circle-fill-animation 3s;

  @keyframes circle-fill-animation {
    0% {
      stroke-dasharray: 0, ${({ totalCircleLength }) => totalCircleLength && totalCircleLength};
    }
  }
`;

const Number = styled.text`
  font-weight: 600;

  fill: ${({ fontColor }) => fontColor && fontColor};
  font-size: ${({ fontSize }) => fontSize && fontSize};
`;

const PercentSymbol = styled.text`
  font-weight: 600;

  fill: ${({ percentColor }) => percentColor && percentColor};
  font-size: ${({ percentSize }) => percentSize && percentSize};
`;

export { AnimatedCircle, Number, PercentSymbol };
