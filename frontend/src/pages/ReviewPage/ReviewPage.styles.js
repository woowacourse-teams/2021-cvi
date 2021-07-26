import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Container = styled.div`
  padding: 2.5rem 8rem 6rem 8rem;
`;

const Title = styled.h2`
  font-size: 2.8rem;
  margin-bottom: 1.4rem;
`;

const ButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
  margin-bottom: 2.6rem;
`;

const ReviewList = styled.ul`
  display: grid;
  grid-template-columns: repeat(2, 1fr);

  & > li:nth-of-type(2n + 1) {
    border-right: 0.15rem solid ${PALETTE.NAVY100};
  }

  & > *:not(:last-child) {
    border-bottom: 0.15rem solid ${PALETTE.NAVY100};
  }
`;

const FrameContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  width: 100%;
`;

export { Container, Title, ReviewList, FrameContent, ButtonWrapper };
