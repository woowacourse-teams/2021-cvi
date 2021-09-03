import { css } from '@emotion/react';
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
  grid-template-columns: 50% 50%;
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
    grid-template-columns: 100%;

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
  height: 6rem;
`;

const TabContainer = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  position: relative;

  @media screen and (max-width: 801px) {
    padding: 1rem 0 1rem 0.6rem;
  }
`;

const tabFrameStyles = css`
  display: flex;
  justify-content: flex-start;
  margin-bottom: 2rem;
  align-items: center;
  min-height: 6rem;
`;

const filterButtonStyles = css`
  line-height: 2;
  margin-right: 2rem;
  position: absolute;
  top: 0.6rem;
  right: 0;

  @media screen and (max-width: 801px) {
    padding-right: 0;
    top: 0.6rem;
    right: 0;
  }
`;

const Option = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 8fr 1fr;
  grid-template-rows: repeat(3, 1fr);
  padding: 1rem 1rem 1rem 3rem;

  & > * {
    display: flex;
    align-items: center;
  }
  & > button {
    grid-column-start: 3;
    grid-column-end: 4;
    grid-row-start: 1;
    grid-row-end: 4;
    justify-self: end;
  }
`;

const closeOptionListButtonStyles = css`
  display: flex;
  justify-content: flex-end;
  height: 5.2rem;
  padding-bottom: 1.2rem;
  padding-right: 0.4rem;
`;

export {
  Container,
  Title,
  ReviewList,
  FrameContent,
  ButtonWrapper,
  ScrollLoadingContainer,
  TabContainer,
  tabFrameStyles,
  filterButtonStyles,
  Option,
  closeOptionListButtonStyles,
};
