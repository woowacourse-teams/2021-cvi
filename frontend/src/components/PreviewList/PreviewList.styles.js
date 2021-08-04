import styled from '@emotion/styled/';
import { PALETTE } from '../../constants';

const Container = styled.ul`
  display: grid;
  grid-template-rows: repeat(2, 1fr);
  grid-template-columns: repeat(3, 1fr);
  position: relative;
  min-height: 36rem;

  & > li {
    border-bottom: 0.1rem solid ${PALETTE.NAVY150};
  }
  & > li:not(:nth-of-type(3n)) {
    border-right: 0.1rem solid ${PALETTE.NAVY150};
  }
  & > li:nth-last-child(-n + 3) {
    border-bottom: none;
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
