import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
`;

const SelectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 2.4rem;
`;

const SelectionTitle = styled.div`
  color: ${FONT_COLOR.BLUE_GRAY};
  font-size: 1.4rem;
  margin: 0 0 0.4rem 1.2rem;
`;

const LoginContainer = styled.div`
  margin-top: 0.4rem;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const Title = styled.div`
  margin-bottom: 4.2rem;
  font-size: 2.8rem;
  font-weight: 500;
`;

const frameStyles = css`
  padding: 4rem;
  flex-direction: column;
`;

const signupButtonStyles = css`
  margin-top: 5.2rem;
  width: 100%;
  height: 5.2rem;
  border-radius: 2.6rem;
  font-size: 1.6rem;
`;

const goLoginStyles = css`
  color: ${FONT_COLOR.LIGHT_GRAY};

  &:hover {
    color: ${FONT_COLOR.GRAY};
  }
`;

const Span = styled.span`
  line-height: 1.5;
`;

export {
  Container,
  LoginContainer,
  SelectionContainer,
  SelectionTitle,
  Title,
  Span,
  frameStyles,
  signupButtonStyles,
  goLoginStyles,
};
