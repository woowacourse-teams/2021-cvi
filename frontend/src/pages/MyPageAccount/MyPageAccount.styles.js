import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

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

  @media screen and (max-width: 1024px) {
    width: 100%;
    display: flex;
    align-items: center;
    padding: 0;
  }
`;

const AgeRange = styled.div`
  font-size: 1.2rem;
  margin-top: 0.8rem;
  margin-bottom: 0.2rem;

  @media screen and (max-width: 514px) {
    margin-right: auto;
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

const inputStyles = css`
  @media screen and (max-width: 514px) {
    width: 100%;
  }
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

  @media screen and (max-width: 514px) {
    width: 100%;
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
  inputStyles,
  disabledStyles,
};
