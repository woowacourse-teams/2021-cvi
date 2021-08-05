import styled from '@emotion/styled';

const Container = styled.div`
  position: absolute;
  top: 0.8rem;
  left: 0.8rem;
  right: 0.8rem;
  bottom: 0.8rem;

  background-color: ${({ backgroundColor }) => backgroundColor && backgroundColor};
  display: ${({ isLoading }) => (isLoading ? 'block' : 'none')};

  @media screen and (max-width: 1024px) {
    left: 0;
  }
`;

const Img = styled.img`
  position: absolute;
  width: ${({ width }) => width && width};
  height: ${({ height }) => height && height};
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 999;
`;

export { Container, Img };