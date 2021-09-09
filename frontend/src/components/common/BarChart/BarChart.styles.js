import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  width: fit-content;
  height: 100%;
  padding: 4rem 2rem 1rem 2rem;
`;

const RegionList = styled.div`
  display: flex;
  height: 100%;

  & > div:nth-of-type(17n) > div {
    right: 0;
    left: auto;
  }
  & > div:nth-of-type(17n-1) > div {
    right: 0;
    left: auto;
  }
  & > div:nth-of-type(17n-16) > div {
    left: 0;
  }
  & > div:nth-of-type(17n-15) > div {
    left: 0;
  }
`;

const RegionItem = styled.div`
  position: relative;
  min-width: 6rem;
  display: flex;
  flex-direction: column;
`;

const HoveredDetail = styled.div`
  position: absolute;
  width: 23rem;
  display: ${({ isShowing }) => (isShowing ? 'flex' : 'none')};
  background-color: ${THEME_COLOR.WHITE};
  box-shadow: 0 0 10px 4px rgba(0, 0, 0, 0.05);
  border-radius: 0.8rem;
  left: -8.4rem;
`;

const DetailContainer = styled.div`
  width: 50%;
  padding: 1.2rem;
`;

const DetailTitle = styled.div`
  font-size: 1rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const DetailCount = styled.div`
  font-size: 1.2rem;
  color: ${FONT_COLOR.BLACK};
  position: relative;
  margin-left: 1.2rem;

  &::before {
    content: '';
    position: absolute;
    top: 52%;
    transform: translateY(-50%);
    left: -1.2rem;
    width: 0.8rem;
    height: 0.8rem;
    border-radius: 50%;
    background-color: ${({ first }) => (first ? '#5A78AB' : THEME_COLOR.PRIMARY)};
  }
`;

const BarList = styled.div`
  display: flex;
  height: 90%;
`;

const BarContainer = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  gap: 0.2rem;

  color: ${({ isShowing }) => (isShowing ? FONT_COLOR.BLACK : FONT_COLOR.LIGHT_GRAY)};
  width: ${({ first }) => first && '3rem'};
  align-items: ${({ first }) => (first ? 'flex-end' : 'flex-start')};
`;

const Line = styled.div`
  border: 1px dashed ${THEME_COLOR.PLACEHOLDER};
  height: ${({ percent }) => 100 - percent * 1.1}%;
  display: ${({ isShowing }) => (isShowing ? 'block' : 'none')};
`;

const Percent = styled.div`
  font-size: 1rem;
`;

const Bar = styled.div`
  border-radius: 0.2rem;
  width: 0.8rem;
  background-color: ${({ isShowing, first }) =>
    isShowing ? (first ? '#5A78AB' : THEME_COLOR.PRIMARY) : first ? '#A1B8DF' : '#96cdd5'};
  height: ${({ percent }) => (percent ? percent : '0')}%;
  ${({ first }) => first && 'margin-right: 0.4rem'};
`;

const Region = styled.div`
  height: 10%;
  display: flex;
  justify-content: center;
  font-size: 1.2rem;
  color: ${({ isShowing }) => (isShowing ? FONT_COLOR.BLACK : FONT_COLOR.LIGHT_GRAY)};
  padding-right: 0.4rem;
`;

export {
  Container,
  RegionList,
  RegionItem,
  BarList,
  BarContainer,
  Line,
  Percent,
  Bar,
  Region,
  HoveredDetail,
  DetailContainer,
  DetailTitle,
  DetailCount,
};
