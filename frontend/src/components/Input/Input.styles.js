import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const InputElement = styled.input`
  vertical-align: middle;
  border-radius: 1.6rem;
  border: 0.1rem solid ${FONT_COLOR.PURPLE_GRAY};
  height: 4rem;
  line-height: 4rem;
  padding: 0.1rem 0 0 1.2rem;
  font-size: 1.6rem;
  color: ${FONT_COLOR.BLACK};

  width: ${({ width }) => width};

  ::placeholder {
    color: ${FONT_COLOR.PURPLE_GRAY};
  }
`;

export { InputElement };
