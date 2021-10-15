import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
`;

const MapContainer = styled.div`
  position: relative;
  width: 33rem;
  max-height: 60rem;

  // 서울특별시
  & > *:nth-of-type(1) {
    top: 9rem;
    left: 12.4rem;
  }

  //부산광역시
  & > *:nth-of-type(2) {
    top: 38rem;
    right: 1rem;
  }

  // 대구광역시
  & > *:nth-of-type(3) {
    bottom: 30rem;
    right: 8rem;
  }

  // 인천광역시
  & > *:nth-of-type(4) {
    top: 12rem;
    left: 1rem;
  }

  // 광주광역시
  & > *:nth-of-type(5) {
    bottom: 16rem;
    left: 9rem;
  }

  // 울산광역시
  & > *:nth-of-type(6) {
    bottom: 28rem;
    right: 1rem;
  }

  // 대전광역시
  & > *:nth-of-type(7) {
    top: 27.5rem;
    left: 11rem;
  }

  // 경기도
  & > *:nth-of-type(8) {
    top: 5rem;
    left: 5rem;
  }

  // 강원도
  & > *:nth-of-type(9) {
    top: 8rem;
    right: 6rem;
  }

  // 세종특별자치시
  & > *:nth-of-type(10) {
    top: 17rem;
    left: 10rem;
  }

  // 충청북도
  & > *:nth-of-type(11) {
    top: 16rem;
    left: 17.5rem;
  }

  // 충청남도
  & > *:nth-of-type(12) {
    top: 21rem;
    left: 2.5rem;
  }

  // 전라북도
  & > *:nth-of-type(13) {
    bottom: 24rem;
    left: 3rem;
  }

  // 전라남도
  & > *:nth-of-type(14) {
    bottom: 14rem;
  }

  // 경상북도
  & > *:nth-of-type(15) {
    top: 18rem;
    right: 1rem;
  }

  // 경상남도
  & > *:nth-of-type(16) {
    bottom: 20rem;
    right: 9rem;
  }

  // 제주특별시
  & > *:nth-of-type(17) {
    bottom: 4rem;
    left: 11rem;
  }
`;

const LoadingContainer = styled.div`
  position: relative;
  top: 50%;
`;

const Info = styled.div`
  position: absolute;
  width: 6.4rem;
  border-radius: 1.2rem;
  box-shadow: 0 0 8px 4px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  font-size: 1.2rem;
  padding: 0.4rem;

  background-color: ${({ isShowing }) =>
    isShowing ? THEME_COLOR.WHITE : 'rgba(255, 255, 255, 0.7)'};
  color: ${({ isShowing }) => (isShowing ? FONT_COLOR.GRAY : 'rgba(107, 108, 111, 0.8)')};
`;

const Title = styled.div`
  display: flex;
  justify-content: center;
  margin: 0.1rem 0;
`;

const Region = styled.div`
  font-size: 1rem;
  font-weight: 500;
  color: ${FONT_COLOR.BLACK};
`;

const InoculationRateContainer = styled.div`
  & > * {
    position: relative;
    padding-left: 1.6rem;

    &::before {
      top: 51%;
      transform: translateY(-50%);
      left: 0.6rem;
      position: absolute;
      content: '';
      width: 0.6rem;
      height: 0.6rem;
      border-radius: 50%;
    }

    &:nth-of-type(1) {
      &::before {
        background-color: #a1b8df;
      }
    }

    &:nth-of-type(2) {
      &::before {
        background-color: #5a78ab;
      }
    }
  }
`;

export { Container, MapContainer, LoadingContainer, Info, Region, InoculationRateContainer, Title };
