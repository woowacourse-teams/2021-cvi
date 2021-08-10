import styled from '@emotion/styled';
import { FONT_COLOR } from '../../../constants';

const Container = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Designer = styled.div`
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const Description = styled.div`
  font-weight: 500;
  margin-top: 2rem;
  font-size: 2rem;

  ${({ styles }) => styles && styles}
`;

export { Container, Designer, Description };
