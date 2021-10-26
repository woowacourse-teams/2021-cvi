import PropTypes from 'prop-types';
import { THEME_COLOR } from '../../../constants';
import { getStatisticalValues } from '../../../utils';
import { MAP_CHART_SVG_PATH } from './MapChartSvgPath';

const MapChartSvg = ({ dataList, hoveredRegion, setHoveredRegion }) => {
  const averageInoculationList = dataList.map((data) => data.totalSecondRate);

  const { mean, standardDeviation } = getStatisticalValues(averageInoculationList);

  const colorClassification = (value) => {
    if (value <= mean - 2 * standardDeviation) return '#D6DFDF';
    else if (value <= mean - standardDeviation) return '#B1CED2';
    else if (value <= mean) return '#8DBEC5';
    else if (value <= mean + standardDeviation) return '#69AEB7';
    else return THEME_COLOR.PRIMARY;
  };

  return (
    <>
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="20 120 400 800" width="330px">
        <defs>
          <style type="text/css"></style>
        </defs>
        <g>
          {dataList.map((region) => (
            <svg key={region.sido}>
              <path
                id={region.sido}
                fill={
                  hoveredRegion === region.sido
                    ? '#2E6F78'
                    : colorClassification(region.totalSecondRate)
                }
                style={{
                  stroke: THEME_COLOR.WHITE,
                  transitionTimingFunction: 'ease',
                  transition: '0.25s',
                }}
                d={MAP_CHART_SVG_PATH[region.sido]}
                onMouseEnter={(event) => setHoveredRegion(event.target.id)}
                onMouseLeave={() => setHoveredRegion('')}
              />
            </svg>
          ))}
        </g>
      </svg>
    </>
  );
};

MapChartSvg.propTypes = {
  dataList: PropTypes.array.isRequired,
  hoveredRegion: PropTypes.string.isRequired,
  setHoveredRegion: PropTypes.func.isRequired,
};

export default MapChartSvg;
