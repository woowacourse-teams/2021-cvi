import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Background = styled.div`
  background-color: ${PALETTE.NAVY300};
  width: 100%;
  height: 100vh;
  padding: 3.2rem 0;
  position: relative;
`;

const Container = styled.div`
  display: flex;
  margin: 0 auto;
  width: 120rem;
  height: 72rem;
  border-radius: 1.6rem;
  background-color: ${PALETTE.NAVY100};
  position: relative;

  /* &::after {
    content: '';
    position: absolute;
    border-radius: 6.4rem 1.6rem 1.6rem 0;
    right: 0;
    width: 28rem;
    height: 100%;
    background-color: ${PALETTE.NAVY150};
  } */
`;

export { Background, Container };
