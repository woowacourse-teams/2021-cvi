import styled from '@emotion/styled';
import { FONT_COLOR } from '../../../constants';

const Container = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  ${({ width }) => `width: ${width && width};`}

  @media screen and (max-width: 1024px) {
    ${({ mobileWidth }) => `width: ${mobileWidth && mobileWidth};`}
  }
`;

const Designer = styled.div`
  color: ${FONT_COLOR.LIGHT_GRAY};

  @media screen and (max-width: 1024px) {
    ${({ mobileWidth }) => `font-size: ${mobileWidth && `1rem`};`}
  }
`;

const Description = styled.div`
  font-weight: 500;
  margin-top: 2rem;
  font-size: 2rem;

  ${({ styles }) => styles && styles}
  @media screen and (max-width: 1024px) {
    ${({ mobileWidth }) => `font-size: ${mobileWidth && `1.2rem`};`}
  }
`;

export { Container, Designer, Description };
