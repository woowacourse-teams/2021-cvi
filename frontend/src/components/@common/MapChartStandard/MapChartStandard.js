import PropTypes from 'prop-types';
import React from 'react';
import { getStatisticalValues } from '../../../utils';
import {
  Container,
  Title,
  StandardContainer,
  InoculationRateContainer,
} from './MapChartStandard.styles';

const MapChartStandard = ({ dataList }) => {
  const averageInoculationList = dataList.map((data) => data.totalSecondRate);
  const { mean, standardDeviation } = getStatisticalValues(averageInoculationList);

  return (
    <Container>
      <Title>완전 접종률(%)</Title>
      <StandardContainer>
        <div>0 ~ {(mean - 2 * standardDeviation).toFixed(1)}</div>
        <div>
          {(mean - 2 * standardDeviation + 0.1).toFixed(1)} ~{' '}
          {(mean - standardDeviation).toFixed(1)}
        </div>
        <div>
          {(mean - standardDeviation + 0.1).toFixed(1)} ~ {mean}
        </div>
        <div>
          {(mean + 0.1).toFixed(1)} ~ {(mean + standardDeviation).toFixed(1)}
        </div>
        <div>
          {(mean + standardDeviation + 0.1).toFixed(1)} ~ {Math.max(...averageInoculationList)}
        </div>
      </StandardContainer>
      <InoculationRateContainer>
        <div>1차 접종률</div>
        <div>완전 접종률</div>
      </InoculationRateContainer>
    </Container>
  );
};

MapChartStandard.propTypes = {
  dataList: PropTypes.array.isRequired,
};

export default MapChartStandard;
