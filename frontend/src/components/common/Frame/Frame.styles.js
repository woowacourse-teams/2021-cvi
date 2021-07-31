import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  border-radius: 1.6rem;
  background-color: ${({ backgroundColor }) => backgroundColor};
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  box-shadow: ${({ showShadow }) =>
    showShadow && 'rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px'};

  ${({ styles }) => styles};

  @media screen and (max-width: 540px) {
    border-radius: 0;
  }
`;

export { Container };
