import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  @media screen and (max-width: 1024px) {
    gap: 2rem;
  }
`;

const Img = styled.img`
  width: 96%;
  height: auto;
  border-radius: 1.6rem;
  margin-top: 1rem;
`;

const frameStyles = css`
  display: flex;
  flex-direction: column;
  position: relative;
`;

const Title = styled.h3`
  margin-bottom: 1rem;

  @media screen and (max-width: 801px) {
    margin: 1rem 1.4rem;
  }
`;

const Source = styled.div`
  position: absolute;
  font-size: 1.2rem;
  color: #acacac;
  bottom: 1.5rem;
  right: 3rem;
`;

export { Container, Img, frameStyles, Title, Source };
