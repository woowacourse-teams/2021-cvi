import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Title = styled.h3`
  margin-bottom: 1rem;

  @media screen and (max-width: 801px) {
    margin: 1rem 1.4rem;
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin: 2rem 0;
  min-height: 50rem;

  @media screen and (max-width: 801px) {
    margin: 2rem 0;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const buttonStyles = css`
  padding-right: 0;

  @media screen and (max-width: 801px) {
    margin: auto 0;
  }
`;

export { Title, Container, ButtonContainer, buttonStyles };
