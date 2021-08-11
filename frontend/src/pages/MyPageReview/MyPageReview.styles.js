import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Container = styled.div`
  width: 100%;
  flex: 1;
  padding: 8.1rem 8rem 6rem 8rem;
  display: flex;
  flex-direction: column;
  height: inherit;
  overflow-y: auto;

  @media screen and (max-width: 1024px) {
    padding: 3rem 0 0 0;
  }
`;

const LottieContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const LoadingContainer = styled.div`
  position: relative;
  top: 35%;
`;

const ScrollLoadingContainer = styled.div`
  position: relative;
  height: 8rem;
`;

const Title = styled.div`
  font-size: 2.8rem;
  margin-bottom: 6.8rem;
  font-weight: 600;

  @media screen and (max-width: 1024px) {
    padding: 0 2rem;
    margin-bottom: 3rem;
  }
`;

const MyReviewList = styled.div`
  position: relative;
  width: 100%;
  & > *:not(:last-child) {
    border-bottom: 0.15rem solid ${PALETTE.NAVY100};
  }
`;

const frameStyle = css`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
`;

export {
  Container,
  LottieContainer,
  LoadingContainer,
  ScrollLoadingContainer,
  Title,
  MyReviewList,
  frameStyle,
};
