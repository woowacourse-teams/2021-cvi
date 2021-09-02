import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  width: 100rem;
`;

const Image = styled.img`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  height: 60rem;
  width: 60rem;
  object-fit: contain;
`;

const nextButtonStyles = css`
  border-radius: 0;
  border-top: none;
  border-left: none;
  width: 3rem;
  height: 3rem;
  transform: rotate(-45deg);
  border-width: 0.6rem;
  margin: 3rem;

  position: absolute;
  top: 50%;
  right: 0;
`;

const previousButtonStyles = css`
  border-radius: 0;
  border-bottom: none;
  border-right: none;
  width: 3rem;
  height: 3rem;
  transform: rotate(-45deg);
  border-width: 0.6rem;
  margin: 3rem;

  position: absolute;
  top: 50%;
  left: 0;
`;

export { Container, Image, nextButtonStyles, previousButtonStyles };
