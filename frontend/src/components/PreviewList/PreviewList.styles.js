import styled from '@emotion/styled/';
import { PALETTE } from '../../constants';

const Container = styled.ul`
  display: grid;
  grid-template-rows: repeat(2, 1fr);
  grid-template-columns: repeat(2, 1fr);

  & > li {
    border-bottom: 0.1rem solid ${PALETTE.NAVY150};
  }
  & > li:nth-of-type(2n + 1) {
    border-right: 0.1rem solid ${PALETTE.NAVY150};
  }
  & > li:nth-last-child(-n + 2) {
    border-bottom: none;
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