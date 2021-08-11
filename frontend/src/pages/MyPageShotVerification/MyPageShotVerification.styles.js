import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  padding: 8rem 8rem 9rem 8rem;
  display: flex;
  flex-direction: column;

  overflow-y: auto;
  @media screen and (max-width: 1024px) {
    padding: 3rem 2rem 8rem 2rem;
  }
`;

const LottieContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Title = styled.div`
  font-size: 2.8rem;
  margin-bottom: 6.8rem;
  font-weight: 600;
`;

const Content = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  align-items: center;
  min-height: 31rem;
`;

const ImageContainer = styled.div`
  width: 100%;
  /* max-width: 40rem; */
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 1.6rem;
  position: relative;

  & > *:nth-of-type(1) {
    padding-top: 3rem;
  }

  @media screen and (max-width: 1024px) {
    flex-direction: column;
    align-items: center;
    gap: 0;
    margin-bottom: 2rem;
  }

  .fileContainer {
    width: fit-content;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    & > input {
      width: 16rem;
    }
  }

  // 이미지 크기(비율) 조절용
  .uploadPictureContainer {
    display: ${({ isFileSelected }) => (isFileSelected ? 'none' : 'block')};
  }

  .errorMessage {
    color: red;

    @media screen and (max-width: 1024px) {
      word-break: break-all;
    }
  }

  .errorsContainer {
    display: flex;
    justify-content: center;
    width: 49rem;
    position: absolute;
    font-size: 1.2rem;
    top: 60px;
    left: 50%;
    transform: translateX(-50%);

    @media screen and (max-width: 1024px) {
      width: 306px;
      top: 115px;
    }
  }
`;

const Image = styled.img`
  width: 50rem;
  margin-bottom: 2rem;

  @media screen and (max-width: 1024px) {
    width: 30rem;
  }
`;

const buttonStyles = css`
  margin: auto auto 0 auto;
  width: 51.5rem;
  height: 5.2rem;
  border-radius: 2.6rem;

  @media screen and (max-width: 516px) {
    width: 100%;
  }
`;

const LoadingContainer = styled.div`
  position: relative;
  width: 100%;
  top: 44%;

  & > *:nth-of-type(2) {
    display: flex;
    justify-content: center;
    position: absolute;
    width: 100%;
    top: 6rem;
  }

  @media screen and (max-width: 1024px) {
    top: 35%;
  }
`;

export {
  Container,
  ImageContainer,
  LottieContainer,
  Title,
  Content,
  Image,
  buttonStyles,
  LoadingContainer,
};
