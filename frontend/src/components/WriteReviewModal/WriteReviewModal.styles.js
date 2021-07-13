import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin: 2.4rem 0;
`;

const TextArea = styled.textarea`
  width: 100%;
  margin-top: 2.4rem;
  min-height: 36rem;
  border: 0.1rem solid ${FONT_COLOR.PURPLE_GRAY};
  border-radius: 1.6rem;
  font-size: 1.6rem;
  padding: 2rem 0.4rem;
  resize: vertical;
`;

const ButtonWrapper = styled.div`
  margin-top: 2.4rem;
  width: 100%;
  display: flex;
  justify-content: flex-end;
`;

export { Container, TextArea, ButtonWrapper };
