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
  HoveredDetail,
  DetailContainer,
  DetailTitle,
  DetailCount,
} from './BarChart.styles';

const BarChart = ({ dataList }) => {
  const [hoveredRegion, setHoveredRegion] = useState('서울특별시');

  return (
    <Container>
      <RegionList>
        {dataList.map((region) => (
          <RegionItem key={region.sido}>
            <HoveredDetail isShowing={hoveredRegion === region.sido}>
              <DetailContainer>
                <DetailTitle>1차 접종</DetailTitle>
                <DetailCount first={true}>{numberWithCommas(region.totalFirstCnt)}명</DetailCount>
              </DetailContainer>
              <DetailContainer>
                <DetailTitle first={false}>완전 접종 </DetailTitle>
                <DetailCount>{numberWithCommas(region.totalSecondCnt)}명</DetailCount>
              </DetailContainer>
            </HoveredDetail>
            <BarList>
              <BarContainer
                first={true}
                isShowing={hoveredRegion === region.sido}
                onMouseEnter={() => setHoveredRegion(region.sido)}
              >
                <Line isShowing={hoveredRegion === region.sido} percent={region.totalFirstRate} />
                <Percent>{region.totalFirstRate}%</Percent>
                <Bar
                  first={true}
                  isShowing={hoveredRegion === region.sido}
                  percent={region.totalFirstRate}
                />
              </BarContainer>
              <BarContainer
                isShowing={hoveredRegion === region.sido}
                onMouseEnter={() => setHoveredRegion(region.sido)}
              >
                <Percent>{region.totalSecondRate}%</Percent>
                <Bar isShowing={hoveredRegion === region.sido} percent={region.totalSecondRate} />
              </BarContainer>
            </BarList>
            <Region isShowing={hoveredRegion === region.sido}>{REGION_NAME[region.sido]}</Region>
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
