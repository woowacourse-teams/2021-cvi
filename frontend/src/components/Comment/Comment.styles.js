import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Container = styled.div`
  border-top: 0.15rem solid ${PALETTE.NAVY100};
  padding: 2rem 0;
`;

const CommentCount = styled.div`
  font-size: 1.8rem;
  margin: 0 3rem;
  font-weight: 500;

  @media screen and (max-width: 1024px) {
    margin: 0 2rem;
  }
`;

const CommentFormContainer = styled.div`
  padding: 2rem 3rem;

  @media screen and (max-width: 1024px) {
    padding: 2rem;
  }
`;
export { Container, CommentCount, CommentFormContainer };
