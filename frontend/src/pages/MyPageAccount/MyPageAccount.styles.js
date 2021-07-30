import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  width: 100%;
  flex: 1;
  padding: 8.1rem 8rem 6rem 8rem;
  display: flex;
  flex-direction: column;
`;

const Title = styled.div`
  font-size: 2.8rem;
  margin-bottom: 6.8rem;
  font-weight: 600;
`;

const ProfileImage = styled.img`
  width: 20rem;
  height: 20rem;
  border-radius: 4rem;
  object-fit: cover;
`;

const InfoContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const Info = styled.div`
  padding: 2rem;
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const AgeRange = styled.div`
  font-size: 1.2rem;
  margin-top: 0.8rem;
  margin-bottom: 0.2rem;
`;

const buttonStyles = css`
  margin: 4rem auto;
  width: 51.5rem;
  height: 5.2rem;
  border-radius: 2.6rem;
`;

const disabledStyles = css`
  margin: 4rem auto;
  width: 51.5rem;
  height: 5.2rem;
  border-radius: 2.6rem;
  background-color: ${FONT_COLOR.LIGHT_GRAY};
  border: 0.1rem solid ${FONT_COLOR.LIGHT_GRAY};
  color: rgba(255, 255, 255, 0.6);

  &:hover {
    background-color: ${FONT_COLOR.LIGHT_GRAY};
    border: 0.1rem solid ${FONT_COLOR.LIGHT_GRAY};
  }
`;

export {
  Container,
  Title,
  ProfileImage,
  InfoContainer,
  Info,
  AgeRange,
  buttonStyles,
  disabledStyles,
};
