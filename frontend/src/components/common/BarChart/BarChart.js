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

const BarChart = () => {
  const [hoverdRegion, setHoveredRegion] = useState('서울특별시');

  const dataList = [
    {
      accumulatedFirstCnt: 3761145,
      accumulatedSecondCnt: 1357940,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 26082,
      secondCnt: 23102,
      sido: '서울특별시',
      totalFirstCnt: 3787227,
      totalSecondCnt: 1381042,
      accumulatedFirstRate: 39.5,
      accumulatedSecondRate: 14.4,
    },
    {
      accumulatedFirstCnt: 1398054,
      accumulatedSecondCnt: 486608,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 10800,
      secondCnt: 5697,
      sido: '부산광역시',
      totalFirstCnt: 1408854,
      totalSecondCnt: 492305,
      accumulatedFirstRate: 41.8,
      accumulatedSecondRate: 14.6,
    },
    {
      accumulatedFirstCnt: 882270,
      accumulatedSecondCnt: 316909,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 8152,
      secondCnt: 5430,
      sido: '대구광역시',
      totalFirstCnt: 890422,
      totalSecondCnt: 322339,
      accumulatedFirstRate: 37.0,
      accumulatedSecondRate: 13.4,
    },
    {
      accumulatedFirstCnt: 1072083,
      accumulatedSecondCnt: 368072,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 9621,
      secondCnt: 5467,
      sido: '인천광역시',
      totalFirstCnt: 1081704,
      totalSecondCnt: 373539,
      accumulatedFirstRate: 36.8,
      accumulatedSecondRate: 12.7,
    },
    {
      accumulatedFirstCnt: 569425,
      accumulatedSecondCnt: 209116,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 6163,
      secondCnt: 3130,
      sido: '광주광역시',
      totalFirstCnt: 575588,
      totalSecondCnt: 212246,
      accumulatedFirstRate: 39.8,
      accumulatedSecondRate: 14.7,
    },
    {
      accumulatedFirstCnt: 542928,
      accumulatedSecondCnt: 201515,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 4600,
      secondCnt: 2667,
      sido: '대전광역시',
      totalFirstCnt: 547528,
      totalSecondCnt: 204182,
      accumulatedFirstRate: 37.6,
      accumulatedSecondRate: 14.0,
    },
    {
      accumulatedFirstCnt: 113686,
      accumulatedSecondCnt: 38707,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 984,
      secondCnt: 893,
      sido: '세종특별자치시',
      totalFirstCnt: 114670,
      totalSecondCnt: 39600,
      accumulatedFirstRate: 31.6,
      accumulatedSecondRate: 10.9,
    },
    {
      accumulatedFirstCnt: 4810996,
      accumulatedSecondCnt: 1674278,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 41937,
      secondCnt: 32848,
      sido: '경기도',
      totalFirstCnt: 4852933,
      totalSecondCnt: 1707126,
      accumulatedFirstRate: 36.0,
      accumulatedSecondRate: 12.7,
    },
    {
      accumulatedFirstCnt: 412551,
      accumulatedSecondCnt: 127961,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 3468,
      secondCnt: 1894,
      sido: '울산광역시',
      totalFirstCnt: 416019,
      totalSecondCnt: 129855,
      accumulatedFirstRate: 36.9,
      accumulatedSecondRate: 11.5,
    },
    {
      accumulatedFirstCnt: 662989,
      accumulatedSecondCnt: 244881,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 5544,
      secondCnt: 3365,
      sido: '충청북도',
      totalFirstCnt: 668533,
      totalSecondCnt: 248246,
      accumulatedFirstRate: 42.7,
      accumulatedSecondRate: 15.8,
    },
    {
      accumulatedFirstCnt: 689827,
      accumulatedSecondCnt: 276911,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 4327,
      secondCnt: 2699,
      sido: '강원도',
      totalFirstCnt: 694154,
      totalSecondCnt: 279610,
      accumulatedFirstRate: 45.2,
      accumulatedSecondRate: 18.2,
    },
    {
      accumulatedFirstCnt: 883105,
      accumulatedSecondCnt: 336840,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 7983,
      secondCnt: 3593,
      sido: '충청남도',
      totalFirstCnt: 891088,
      totalSecondCnt: 340433,
      accumulatedFirstRate: 42.1,
      accumulatedSecondRate: 16.1,
    },
    {
      accumulatedFirstCnt: 813721,
      accumulatedSecondCnt: 316909,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 5322,
      secondCnt: 3549,
      sido: '전라북도',
      totalFirstCnt: 819043,
      totalSecondCnt: 320458,
      accumulatedFirstRate: 45.6,
      accumulatedSecondRate: 17.8,
    },
    {
      accumulatedFirstCnt: 886952,
      accumulatedSecondCnt: 349187,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 5918,
      secondCnt: 3248,
      sido: '전라남도',
      totalFirstCnt: 892870,
      totalSecondCnt: 352435,
      accumulatedFirstRate: 48.4,
      accumulatedSecondRate: 19.1,
    },
    {
      accumulatedFirstCnt: 1113486,
      accumulatedSecondCnt: 417103,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 11431,
      secondCnt: 4966,
      sido: '경상북도',
      totalFirstCnt: 1124917,
      totalSecondCnt: 422069,
      accumulatedFirstRate: 42.7,
      accumulatedSecondRate: 16.0,
    },
    {
      accumulatedFirstCnt: 1295925,
      accumulatedSecondCnt: 472398,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 10044,
      secondCnt: 5820,
      sido: '경상남도',
      totalFirstCnt: 1305969,
      totalSecondCnt: 478218,
      accumulatedFirstRate: 40.4,
      accumulatedSecondRate: 14.8,
    },
    {
      accumulatedFirstCnt: 262185,
      accumulatedSecondCnt: 95259,
      baseDate: '2021-08-05 00:00:00',
      firstCnt: 2548,
      secondCnt: 1297,
      sido: '제주특별자치도',
      totalFirstCnt: 264733,
      totalSecondCnt: 96556,
      accumulatedFirstRate: 39.2,
      accumulatedSecondRate: 14.3,
    },
  ];

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

export default BarChart;
