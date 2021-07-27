import styled from '@emotion/styled';

const AVATAR_SIZE_TYPE = Object.freeze({
  SMALL: 'SMALL',
  MEDIUM: 'MEDIUM',
  LARGE: 'LARGE',
});

const avatarStyle = {
  SMALL: {
    height: '2.4rem',
    width: '2.4rem',
    borderRadius: '1.2rem',
  },
  MEDIUM: {
    height: '3.6rem',
    width: '3.6rem',
    borderRadius: '1.8rem',
  },
  LARGE: {
    height: '12rem',
    width: '12rem',
    borderRadius: '6rem',
  },
};

const Container = styled.div`
  min-height: 2rem;
  min-width: 2rem;
  border-radius: 1rem;

  background-size: cover;
  background-image: url(${({ src }) => src});

  ${({ sizeType }) => avatarStyle[sizeType] || avatarStyle[AVATAR_SIZE_TYPE.MEDIUM]}
  ${({ styles }) => styles && styles}
`;

export { Container, AVATAR_SIZE_TYPE };
