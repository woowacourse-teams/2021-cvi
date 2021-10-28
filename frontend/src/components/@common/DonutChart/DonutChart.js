import { animated, useSpring } from 'react-spring';
import PropTypes from 'prop-types';
import { THEME_COLOR } from '../../../constants';
import { AnimatedCircle } from './DonutChart.styles';

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
  const { number } = useSpring({
    from: { number: 0 },
    number: target,
    config: { duration: 3000 },
  });

  const totalCircleLength = 2 * Math.PI * radius;
  const circleOffset = (totalCircleLength / 4).toString();

  const getStrokeDasharray = (target) => {
    const targetPathLength = (totalCircleLength / 100) * target;

    return `${targetPathLength} ${totalCircleLength - targetPathLength}`;
  };

  return (
    <>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 100 100"
        width={radius * 2}
        height={radius * 2}
      >
        <circle stroke={emptyColor} strokeWidth={thickness} fill="none" r="42" cx="50" cy="50" />
        {target && (
          <AnimatedCircle
            stroke={filledColor}
            strokeWidth={thickness}
            strokeDasharray={getStrokeDasharray(target)}
            strokeDashoffset={circleOffset}
            fill="none"
            r="42"
            cx="50"
            cy="50"
            totalCircleLength={totalCircleLength}
          />
        )}
        <animated.text x="22%" y="59%" fill={fontColor} fontSize={fontSize} fontWeight="700">
          {number.to((n) => n.toFixed(1))}
        </animated.text>
        <text x="69%" y="58%" fill={fontColor} fontSize={percentSize} fontWeight="700">
          %
        </text>
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
  radius: 42,
  thickness: 16,
};

export default DonutChart;
