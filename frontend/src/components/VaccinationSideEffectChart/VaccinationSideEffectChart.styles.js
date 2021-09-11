import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  padding: 1.6rem;

  & > *:nth-of-type(1) > :nth-of-type(2) {
    background-color: #00acc1;
  }

  & > *:nth-of-type(2) > :nth-of-type(2) {
    background-color: #26c6da;
  }

  & > *:nth-of-type(3) > :nth-of-type(2) {
    background-color: #26c6da;
  }

  & > *:nth-of-type(4) > :nth-of-type(2) {
    background-color: #80deea;
  }

  & > *:nth-of-type(5) > :nth-of-type(2) {
    background-color: #b2ebf2;
  }
`;

const SideEffectContainer = styled.div`
  display: flex;
  align-items: center;
  padding: 0.5rem 0;
`;

const SideEffect = styled.div`
  width: 11rem;
  -webkit-animation: fade-in-text 2.2s 0.1s forwards;
  -moz-animation: fade-in-text 2.2s 0.1s forwards;
  animation: fade-in-text 2.2s 0.1s forwards;

  @keyframes fade-in-text {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }
`;

const Bar = styled.div`
  border-radius: 0.3rem;
  height: 3.5rem;
  width: ${({ percentage }) => (percentage ? `${percentage * 0.13}vw` : '0')};

  -webkit-animation: bar-animation 1.2s ${({ index }) => `0.${index + 1}`}s forwards;
  -moz-animation: bar-animation 1.2s ${({ index }) => `0.${index + 1}`}s forwards;
  animation: bar-animation 1.2s ${({ index }) => `0.${index + 1}`}s forwards;

  @keyframes bar-animation {
    0% {
      width: 0;
    }
  }

  @media only screen and (min-width: 1025px) and (max-width: 1300px) {
    width: ${({ percentage }) => (percentage ? `${percentage * 0.1}vw` : '0')};
  }

  @media screen and (max-width: 1024px) {
    width: ${({ percentage }) => (percentage ? `${percentage * 0.7}vw` : '0')};
  }
`;

const Percentage = styled.div`
  margin-left: 0.5rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
  font-size: 1.4rem;
`;

export { Container, SideEffectContainer, SideEffect, Bar, Percentage };
