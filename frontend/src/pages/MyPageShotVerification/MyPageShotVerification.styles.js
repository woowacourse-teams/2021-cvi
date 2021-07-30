import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  flex: 1;
  padding: 8rem 8rem 6rem 8rem;

  display: flex;
  flex-direction: column;

  @media screen and (max-width: 1024px) {
    padding: 3rem 2rem;
  }
`;

const Title = styled.div`
  font-size: 2.8rem;
  margin-bottom: 6.8rem;
  font-weight: 600;
`;

const Content = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  align-items: center;
`;

const Image = styled.img`
  width: 20rem;
  margin-bottom: 2rem;
`;

const Label = styled.label`
  @media screen and (max-width: 1024px) {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
`;

const Input = styled.input`
  margin-left: 1.6rem;
  width: 16rem;

  @media screen and (max-width: 1024px) {
    margin-top: 1.2rem;
    margin-left: 0;
  }
`;

const buttonStyles = css`
  margin: 4rem auto;
  width: 51.5rem;
  height: 5.2rem;
  border-radius: 2.6rem;

  @media screen and (max-width: 514px) {
    width: 100%;
  }
`;

export { Container, Title, Content, Image, Label, Input, buttonStyles };
