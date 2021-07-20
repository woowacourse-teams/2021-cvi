import styled from '@emotion/styled';
import { PALETTE } from '../../constants';

const Background = styled.div`
  display: flex;
  background-color: ${PALETTE.NAVY300};
  width: 100%;
  height: 100%;
  position: relative;
`;

const Container = styled.div`
  display: flex;
  margin: auto;
  width: 132rem;
  height: 76rem;
  border-radius: 1.6rem;
  background-color: ${PALETTE.NAVY100};
  position: relative;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
  z-index: 0;

  &::after {
    content: '';
    position: absolute;
    border-radius: 6.4rem 1.6rem 1.6rem 0;
    right: 0;
    width: 36rem;
    height: 100%;
    background-color: ${PALETTE.NAVY150};
    z-index: -1;
  }
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2.5rem 6rem 2.5rem 6rem;
  flex: 1;
  overflow: auto;
  width: 100%;
`;

const TopContainer = styled.div`
  min-height: 3.6rem;
  display: flex;
  gap: 0.6rem;
  justify-content: flex-end;
`;

export { Background, Container, MainContainer, TopContainer };
