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
  align-items: center;

  @media screen and (max-width: 1024px) {
    padding: 3rem 0 0 0;
  }
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
  & > *:not(:last-child) {
    border-bottom: 0.15rem solid ${PALETTE.NAVY100};
  }
`;

const MyReview = styled.div``;

export { Container, Title, MyReviewList, MyReview };
