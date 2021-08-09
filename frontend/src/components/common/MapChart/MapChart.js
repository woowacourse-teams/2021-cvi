import PropTypes from 'prop-types';
import { useState } from 'react';
import { REGION_NAME } from '../../../constants';
import MapChartStandard from '../MapChartStandard/MapChartStandard';
import MapChartSvg from '../MapChartSvg/MapChartSvg';
import {
  Info,
  Container,
  MapContainer,
  Title,
  Region,
  InoculationRateContainer,
} from './MapChart.styles';

const MapChart = ({ dataList }) => {
  const [hoveredRegion, setHoveredRegion] = useState('서울특별시');

  return (
    <Container>
      <MapContainer>
        {dataList.map((region) => {
          return (
            <Info
              key={region.sido}
              isShowing={hoveredRegion === region.sido}
              onMouseEnter={() => setHoveredRegion(region.sido)}
              onMouseLeave={() => setHoveredRegion('')}
            >
              <Title>
                <Region>{REGION_NAME[region.sido]}</Region>
              </Title>
              <InoculationRateContainer>
                <div>{region.totalFirstRate}%</div>
                <div>{region.totalSecondRate}%</div>
              </InoculationRateContainer>
            </Info>
          );
        })}
        <MapChartSvg
          dataList={dataList}
          hoveredRegion={hoveredRegion}
          setHoveredRegion={setHoveredRegion}
        />
        <MapChartStandard dataList={dataList} />
      </MapContainer>
    </Container>
  );
};

MapChart.propTypes = {
  dataList: PropTypes.array.isRequired,
};

export default MapChart;
