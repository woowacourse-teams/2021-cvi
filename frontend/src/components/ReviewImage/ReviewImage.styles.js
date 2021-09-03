import styled from '@emotion/styled';

const Container = styled.div`
  width: fit-content;
  position: relative;
  cursor: pointer;
`;

const IconWrapper = styled.div`
  position: absolute;
  top: 1.2rem;
  right: 3.2rem;
`;

const Image = styled.img`
  max-width: 100%;
  height: auto;
  padding: 0 2rem 3rem 2rem;
`;

export { Container, IconWrapper, Image };
