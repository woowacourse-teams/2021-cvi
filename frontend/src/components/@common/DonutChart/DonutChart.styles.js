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

export { AnimatedCircle };
