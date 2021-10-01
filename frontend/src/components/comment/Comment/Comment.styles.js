import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../../constants';

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

const ButtonContainer = styled.div`
  width: 100%;
  border-top: 0.15rem solid ${PALETTE.NAVY100};
  display: flex;
  justify-content: center;
  padding-top: 1.6rem;
`;

const buttonStyles = css`
  margin-right: 0.6rem;
  &::after {
    content: '';
    height: 0.6rem;
    width: 0.6rem;
    border-left: 0.1rem solid ${FONT_COLOR.GRAY};
    border-bottom: 0.1rem solid ${FONT_COLOR.GRAY};
    transform: rotate(-45deg);
    margin-left: 0.6rem;
  }
`;

export { Container, CommentCount, CommentFormContainer, ButtonContainer, buttonStyles };
