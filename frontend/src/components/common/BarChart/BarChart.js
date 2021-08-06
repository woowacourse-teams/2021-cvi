import PropTypes from 'prop-types';
import { useState } from 'react';
import { REGION_NAME } from '../../../constants';
import { numberWithCommas } from '../../../utils';
import {
  Container,
  RegionList,
  RegionItem,
  BarList,
  BarContainer,
  Line,
  Percent,
  Bar,
  Region,
  HoverdDetail,
  DetailContainer,
  DetailTitle,
  DetailCount,
} from './BarChart.styles';

const BarChart = ({ dataList }) => {
  const [hoverdRegion, setHoveredRegion] = useState('서울특별시');

  return (
    <Container>
      <RegionList>
        {dataList.map((region) => (
          <RegionItem key={region.sido}>
            <HoverdDetail isShowing={hoverdRegion === region.sido}>
              <DetailContainer>
                <DetailTitle>1차 접종</DetailTitle>
                <DetailCount first={true}>{numberWithCommas(region.totalFirstCnt)}명</DetailCount>
              </DetailContainer>
              <DetailContainer>
                <DetailTitle first={false}>완전 접종 </DetailTitle>
                <DetailCount>{numberWithCommas(region.totalSecondCnt)}명</DetailCount>
              </DetailContainer>
            </HoverdDetail>
            <BarList>
              <BarContainer
                first={true}
                isShowing={hoverdRegion === region.sido}
                onMouseEnter={() => setHoveredRegion(region.sido)}
              >
                <Line
                  isShowing={hoverdRegion === region.sido}
                  percent={region.accumulatedFirstRate}
                />
                <Percent>{region.accumulatedFirstRate}%</Percent>
                <Bar
                  first={true}
                  isShowing={hoverdRegion === region.sido}
                  percent={region.accumulatedFirstRate}
                />
              </BarContainer>
              <BarContainer
                isShowing={hoverdRegion === region.sido}
                onMouseEnter={() => setHoveredRegion(region.sido)}
              >
                <Percent>{region.accumulatedSecondRate}%</Percent>
                <Bar
                  isShowing={hoverdRegion === region.sido}
                  percent={region.accumulatedSecondRate}
                />
              </BarContainer>
            </BarList>
            <Region isShowing={hoverdRegion === region.sido}>{REGION_NAME[region.sido]}</Region>
          </RegionItem>
        ))}
      </RegionList>
    </Container>
  );
};

BarChart.propTypes = {
  dataList: PropTypes.array.isRequired,
};

export default BarChart;