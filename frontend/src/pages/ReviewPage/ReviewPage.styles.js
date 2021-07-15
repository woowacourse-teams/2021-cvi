import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Container = styled.div`
  width: 100%;
  padding: 6rem;
  flex: 1;
  overflow-y: auto;
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
  display: flex;
  flex-direction: column;

  & > *:not(:last-child) {
    border-bottom: 0.2rem solid ${PALETTE.GRAY100};
  }
`;

const FrameContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

export { Container, Title, ReviewList, FrameContent, ButtonWrapper };
