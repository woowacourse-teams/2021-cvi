import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  padding: 2.5rem 8rem 6rem 8rem;
  display: flex;
  flex-direction: column;

  @media screen and (max-width: 1024px) {
    padding: 3rem 0 0 0;
  }
`;

const Content = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-auto-rows: 15rem;
  column-gap: 3.2rem;
  row-gap: 3.2rem;

  & > *:nth-of-type(3n-2) {
    grid-column-start: 1;
    grid-column-end: 3;
    grid-row-start: 1;
    grid-row-end: 2;
  }

  & > *:nth-of-type(3n-1) {
    grid-column-start: 1;
    grid-column-end: 3;
    grid-row-start: 2;
    grid-row-end: 6;
  }

  & > *:nth-of-type(3n) {
    grid-column-start: 3;
    grid-column-end: 4;
    grid-row-start: 1;
    grid-row-end: 6;
  }

  @media screen and (max-width: 1024px) {
    display: flex;
    flex-direction: column;
  }
`;

const Title = styled.h2`
  font-size: 2.8rem;
  margin-bottom: 2.8rem;

  @media screen and (max-width: 1024px) {
    margin-left: 2rem;
  }
`;

const Source = styled.div`
  font-size: 1.2rem;
  color: ${FONT_COLOR.GRAY};
  margin: 3.2rem auto 0 auto;

  @media screen and (max-width: 1024px) {
    margin-bottom: 2rem;
  }
`;

export { Container, Content, Title, Source };
