import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin: 2rem 0 0 0;
  width: 100%;
`;

const TextArea = styled.textarea`
  width: 100%;
  margin: 2.4rem 0;
  height: 36rem;
  border: 0.1rem solid ${FONT_COLOR.BLUE_GRAY};
  border-radius: 1.6rem;
  font-size: 1.6rem;
  padding: 2rem;
  resize: none;
  color: ${FONT_COLOR.BLACK};
`;

const ButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
`;

const buttonStyles = css`
  width: 100%;
  height: 4.4rem;
  border-radius: 1.6rem;
`;

export { Container, TextArea, ButtonWrapper, buttonStyles };
