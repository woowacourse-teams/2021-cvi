import styled from '@emotion/styled/';
import { PALETTE } from '../../../constants';

const Container = styled.ul`
  display: flex;
  flex-direction: column;
  position: relative;
  min-height: 36rem;

  & > li:not(:last-child) {
    border-bottom: 0.1rem solid ${PALETTE.NAVY150};
  }

  @media screen and (max-width: 801px) {
    display: block;
    grid-template-columns: repeat(1, 1fr);

    & > li:not(:last-child) {
      border-bottom: 0.1rem solid ${PALETTE.NAVY150};
      border-right: none;
    }
  }
`;

const Error = styled.div`
  font-size: 2rem;
  display: flex;
  justify-content: center;
  padding-top: 14rem;
  height: 36rem;
`;

export { Container, Error };
