import styled from '@emotion/styled';

const AVATAR_SIZE_TYPE = Object.freeze({
  SMALL: 'SMALL',
  MEDIUM: 'MEDIUM',
  LARGE: 'LARGE',
});

const avatarStyle = {
  SMALL: {
    height: '3.6rem',
    width: '3.6rem',
    border: '1.8rem',
  },
};

const Container = styled.div`
  min-height: 3.6rem;
  min-width: 3.6rem;
  border-radius: 1.8rem;

  background-size: cover;
  background-image: url(${({ src }) => src});

  ${({ sizeType }) => avatarStyle[sizeType]}
`;

export { Container, AVATAR_SIZE_TYPE };
