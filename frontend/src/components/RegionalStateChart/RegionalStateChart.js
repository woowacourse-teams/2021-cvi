import React, { useState } from 'react';
import { Frame, ToggleButton } from '../common';
import { frameStyle } from './RegionalStateChart.styles';

const RegionalStateChart = () => {
  const [selected, setSelected] = useState(false);

  const selectionList = ['막대', '지도'];

  return (
    <Frame width="100%" styles={frameStyle}>
      <ToggleButton
        selectionList={selectionList}
        selected={selected}
        toggleSelected={() => setSelected(!selected)}
      />
      {selected ? <div>barChart</div> : <div>mapChart</div>}
    </Frame>
  );
};

export default RegionalStateChart;
