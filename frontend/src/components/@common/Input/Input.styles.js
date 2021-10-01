import styled from '@emotion/styled';
import { FONT_COLOR } from '../../../constants';

const Label = styled.label`
  display: flex;
  flex-direction: column;
  font-size: 1.2rem;

  ${({ labelStyles }) => labelStyles && labelStyles}
`;

const LabelText = styled.span`
  margin: 0 0 0.3rem 0.4rem;
`;

const InputElement = styled.input`
  vertical-align: middle;
  border-radius: 2.6rem;
  border: 0.1rem solid ${FONT_COLOR.BLUE_GRAY};
  height: 5.2rem;
  line-height: 4rem;
  padding: 0.2rem 0 0 2.2rem;
  font-size: 1.6rem;
  color: ${FONT_COLOR.BLACK};

  width: ${({ width }) => width};

  ::placeholder {
    color: ${FONT_COLOR.BLUE_GRAY};
  }

  ${({ inputStyles }) => inputStyles && inputStyles}
`;

export { Label, LabelText, InputElement };
