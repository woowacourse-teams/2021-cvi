import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  width: fit-content;
  position: relative;
  cursor: pointer;
`;

const DetailIconWrapper = styled.div`
  position: absolute;
  top: 1.2rem;
  right: 3.2rem;
`;

const CloseIconWrapper = styled.div`
  position: absolute;
  top: 0rem;
  right: 1.6rem;
`;

const deleteImageButtonStyles = css`
  position: absolute;
  top: -0.6rem;
  right: -0.6rem;
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  font-size: 1.8rem;
  padding: 0 0 0.2rem 0;
  transform: rotate(45deg);
  font-weight: 100;
`;

const Image = styled.img`
  max-width: 100%;
  height: auto;
  padding: 0 2rem 3rem 2rem;

  ${({ width }) => width && `width: ${width}`};
`;

export { Container, DetailIconWrapper, CloseIconWrapper, Image, deleteImageButtonStyles };
