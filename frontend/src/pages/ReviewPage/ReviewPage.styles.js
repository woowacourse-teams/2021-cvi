import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Container = styled.div`
  padding: 2.5rem 8rem 6rem 8rem;

  @media screen and (max-width: 801px) {
    padding: 3rem 0 0 0;
  }
`;

const Title = styled.h2`
  font-size: 2.8rem;
  margin-bottom: 1.4rem;

  @media screen and (max-width: 801px) {
    margin-left: 2rem;
  }
`;

const ButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
  margin-bottom: 2.6rem;

  @media screen and (max-width: 801px) {
    padding-right: 1rem;
  }
`;

const ReviewList = styled.ul`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  position: relative;
  min-height: 60vh;

  & > li:nth-of-type(2n + 1) {
    border-right: 0.15rem solid ${PALETTE.NAVY100};
  }

  & > *:not(:last-child) {
    border-bottom: 0.15rem solid ${PALETTE.NAVY100};
  }

  @media screen and (max-width: 801px) {
    display: grid;
    grid-template-columns: repeat(1, 1fr);

    & > li:not(:last-child) {
      border-bottom: 0.15rem solid ${PALETTE.NAVY100};
      border-right: none;
    }
  }
`;

const FrameContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  width: 100%;
`;

const ScrollLoadingContainer = styled.div`
  position: relative;
  height: 8rem;
`;

export { Container, Title, ReviewList, FrameContent, ButtonWrapper, ScrollLoadingContainer };
