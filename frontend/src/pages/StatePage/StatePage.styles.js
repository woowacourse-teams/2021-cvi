import styled from '@emotion/styled';

const Container = styled.div`
  padding: 2.5rem 8rem 6rem 8rem;

  @media screen and (max-width: 801px) {
    padding: 3rem 0 0 0;
  }
`;
const RegionalContainer = styled.div`
  margin-top: 2.4rem;
  width: 100%;
  display: grid;
  grid-template-columns: 2fr 1fr;
  column-gap: 2.4rem;

  @media screen and (max-width: 801px) {
    grid-template-columns: 1fr;
    column-gap: 0;
  }
`;

const Title = styled.h2`
  font-size: 2.8rem;
  margin-bottom: 3.2rem;

  @media screen and (max-width: 801px) {
    margin-left: 2rem;
  }
`;

export { Container, Title, RegionalContainer };
