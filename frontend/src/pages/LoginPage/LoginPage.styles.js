import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  flex: 1;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 18rem 6rem;
`;

const Title = styled.div`
  font-size: 2.8rem;
  font-weight: 500;
  margin-bottom: 3.6rem;
`;

const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

const frameStyles = css`
  flex-direction: column;
  padding: 4rem;
`;

const loginButtonStyles = css`
  margin-top: 2.4rem;
  width: 100%;
  height: 4rem;
`;

const signupButtonStyles = css`
  margin-top: 0.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};

  &:hover {
    color: ${FONT_COLOR.GRAY};
  }
`;

export { Title, Container, ButtonContainer, frameStyles, loginButtonStyles, signupButtonStyles };
