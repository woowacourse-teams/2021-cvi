import { css } from '@emotion/react';
import styled from '@emotion/styled';

const frameStyle = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
  height: 50rem;
  max-width: 100%;
  overflow-x: hidden;
`;

const Content = styled.div`
  width: 100%;
  height: 100%;
  overflow-x: auto;
`;

export { frameStyle, Content };
