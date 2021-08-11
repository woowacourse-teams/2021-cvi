import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  font-size: 1.4rem;
  color: ${({ shotVerification }) => (shotVerification ? FONT_COLOR.GREEN : FONT_COLOR.GRAY)};

  ${({ styles }) => styles && styles}
`;

export { Container };
