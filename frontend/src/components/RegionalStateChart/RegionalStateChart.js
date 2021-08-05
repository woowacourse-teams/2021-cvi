import React, { useState } from 'react';
import { BarChart, Frame, ToggleButton } from '../common';
import { frameStyle, Content, CountingDate } from './RegionalStateChart.styles';

const RegionalStateChart = () => {
  const [selected, setSelected] = useState(false);

  const selectionList = ['막대', '지도'];

  const getCountingDate = () => {
    const today = new Date();
    const yesterday = new Date(Date.now() - 86400000);
    const currentHour = today.getHours();

    const year = currentHour < 10 ? yesterday.getFullYear() : today.getFullYear();
    const month =
      currentHour < 10
        ? ('0' + (1 + yesterday.getMonth())).slice(-2)
        : ('0' + (1 + today.getMonth())).slice(-2);
    const day =
      currentHour < 10 ? ('0' + yesterday.getDate()).slice(-2) : ('0' + today.getDate()).slice(-2);

    return `${year}.${month}.${day}`;
  };

  return (
    <Frame width="100%" styles={frameStyle}>
      <ToggleButton
        selectionList={selectionList}
        selected={selected}
        toggleSelected={() => setSelected(!selected)}
      />
      <Content>{selected ? <BarChart /> : <div>mapChart</div>}</Content>
      <CountingDate>국내현황 {getCountingDate()} 00:00 집계 기준</CountingDate>
    </Frame>
  );
};

export default RegionalStateChart;
