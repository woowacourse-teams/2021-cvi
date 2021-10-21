import PropTypes from 'prop-types';
import { THEME_COLOR } from '../../../constants';
import { AnimatedCircle, Number, PercentSymbol } from './DonutChart.styles';

const DonutChart = ({
  target,
  filledColor,
  emptyColor,
  fontColor,
  fontSize,
  percentSize,
  radius,
  thickness,
}) => {
  const totalCircleLength = document.querySelector('circle')?.getTotalLength();
  const circleOffset = (totalCircleLength / 4).toString();

  const getStrokeDasharray = (target) => {
    const targetPathLength = (totalCircleLength / 100) * target;

    return `${targetPathLength} ${totalCircleLength - targetPathLength}`;
  };

  return (
    <>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="-32 -32 100 100"
        width={radius * 2}
        height={radius * 2}
      >
        <circle stroke={emptyColor} strokeWidth={thickness} fill="none" r="40" cx="18" cy="18" />
        {target && (
          <AnimatedCircle
            stroke={filledColor}
            strokeWidth={thickness}
            strokeDasharray={getStrokeDasharray(target)}
            strokeDashoffset={circleOffset}
            fill="none"
            r="40"
            cx="18"
            cy="18"
            totalCircleLength={totalCircleLength}
          />
        )}
        <Number x="-10%" y="26%" fontColor={fontColor} fontSize={fontSize}>
          {target}
        </Number>
        <PercentSymbol x="36%" y="26%" percentColor={fontColor} percentSize={percentSize}>
          %
        </PercentSymbol>
      </svg>
    </>
  );
};

DonutChart.propTypes = {
  target: PropTypes.number.isRequired,
  filledColor: PropTypes.string,
  emptyColor: PropTypes.string,
  fontColor: PropTypes.string,
  fontSize: PropTypes.string,
  percentSize: PropTypes.string,
  radius: PropTypes.number,
  thickness: PropTypes.number,
};

DonutChart.defaultProps = {
  filledColor: THEME_COLOR.PRIMARY,
  emptyColor: '#EFF5F5',
  fontColor: THEME_COLOR.PRIMARY,
  fontSize: '2.2rem',
  percentSize: '1.2rem',
  radius: 52,
  thickness: 16,
};

export default DonutChart;
