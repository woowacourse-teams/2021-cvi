import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  height: 100%;
  padding: 2.5rem 8rem 6rem 8rem;
  flex-direction: column;
  gap: 7.2rem;

  @media screen and (max-width: 1024px) {
    padding: 2.4rem 0 0 0;
    gap: 3rem;
  }
`;

export { Container };
