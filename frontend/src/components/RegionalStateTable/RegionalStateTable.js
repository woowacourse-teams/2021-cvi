import PropTypes from 'prop-types';
import React from 'react';
import { REGION_NAME } from '../../constants';
import { numberWithCommas } from '../../utils';
import { Frame } from '../common';
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Span,
  IncreaseIcon,
  frameStyle,
} from './RegionalStateTable.styles';

const RegionalStateTable = ({ vaccinationStateList }) => {
  return (
    <Frame width="100%" showShadow={true} styles={frameStyle}>
      <Table>
        <Thead>
          <Tr>
            <th>지역</th>
            <th>신규 접종자</th>
            <th>1차 접종자</th>
            <th>완전 접종자</th>
          </Tr>
        </Thead>
        <Tbody>
          {vaccinationStateList.map((region) => (
            <Tr key={region.sido}>
              <td>{REGION_NAME[region.sido]}</td>
              <td>
                {numberWithCommas(region.firstCnt)}
                <Span>
                  명 <IncreaseIcon>▴</IncreaseIcon>
                </Span>
              </td>
              <td>
                {numberWithCommas(region.totalFirstCnt)}
                <Span>명</Span>
              </td>
              <td>
                {numberWithCommas(region.totalSecondCnt)}
                <Span>명</Span>
              </td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </Frame>
  );
};

RegionalStateTable.propTypes = {
  vaccinationStateList: PropTypes.array.isRequired,
};

export default RegionalStateTable;
