import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  bottom: 1.6rem;
  right: -4rem;
  font-size: 1rem;
  position: absolute;
  margin-right: 4rem;
  white-space: nowrap;
  color: ${FONT_COLOR.GRAY};
`;

const Title = styled.div`
  font-size: 1rem;
`;

const StandardContainer = styled.div`
  & > * {
    position: relative;
    padding-left: 1.6rem;

    &::before {
      position: absolute;
      top: 29%;
      left: 0.1rem;
      content: '';
      width: 0.8rem;
      height: 0.8rem;
      border-radius: 50%;
    }

    &:nth-of-type(1) {
      &::before {
        background-color: #d6dfdf;
      }
    }

    &:nth-of-type(2) {
      &::before {
        background-color: #b1ced2;
      }
    }

    &:nth-of-type(3) {
      &::before {
        background-color: #8dbec5;
      }
    }

    &:nth-of-type(4) {
      &::before {
        background-color: #69aeb7;
      }
    }

    &:nth-of-type(5) {
      &::before {
        background-color: ${THEME_COLOR.PRIMARY};
      }
    }
  }
`;

const InoculationRateContainer = styled.div`
  & > * {
    position: relative;
    padding-left: 1.6rem;

    &::before {
      position: absolute;
      top: 28%;
      left: 0.1rem;
      content: '';
      width: 0.8rem;
      height: 0.8rem;
      border-radius: 50%;
    }

    &:nth-of-type(1) {
      margin-top: 1rem;

      &::before {
        background-color: #a1b8df;
      }
    }

    &:nth-of-type(2) {
      &::before {
        background-color: #5a78ab;
      }
    }
  }
`;

export { Container, Title, StandardContainer, InoculationRateContainer };
