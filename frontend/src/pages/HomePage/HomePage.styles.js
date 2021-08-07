import styled from '@emotion/styled';

const Container = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 2.5rem 8rem 6rem 8rem;
  flex-direction: column;
  column-gap: 2.4rem;
  row-gap: 6rem;

  & > *:nth-of-type(1) {
    grid-column-start: 1;
    grid-column-end: 4;
  }

  @media screen and (max-width: 1024px) {
    padding: 2.4rem 0 0 0;
    gap: 3rem;
  }
`;

export { Container };
