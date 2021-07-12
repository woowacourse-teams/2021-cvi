import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  border-radius: 1.6rem;
  background-color: ${({ backgroundColor }) => backgroundColor};
  width: ${({ width }) => width};
  height: ${({ height }) => height};

  ${({ styles }) => styles};
`;

export { Container };
