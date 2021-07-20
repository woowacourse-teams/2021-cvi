import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
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
  margin-top: 3.6rem;
  border-radius: 2.6rem;
  width: 100%;
  height: 5.2rem;
  font-size: 1.6rem;
`;

const goSignupStyles = css`
  margin-top: 0.6rem;
  color: ${FONT_COLOR.LIGHT_GRAY};

  &:hover {
    color: ${FONT_COLOR.GRAY};
  }
`;

export { Title, Container, ButtonContainer, frameStyles, loginButtonStyles, goSignupStyles };
