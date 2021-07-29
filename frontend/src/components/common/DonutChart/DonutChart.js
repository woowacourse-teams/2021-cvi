import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { THEME_COLOR } from '../../../constants';
import { Container, Span, Percent } from './DonutChart.styles';

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
  const [currentNumber, setCurrentNumber] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (currentNumber < target) setCurrentNumber((currentNumber) => currentNumber + 1);
    }, 10);

    return () => clearInterval(intervalId);
  }, [currentNumber]);

  return (
    <Container
      currentNumber={currentNumber}
      filledColor={filledColor}
      emptyColor={emptyColor}
      radius={radius}
    >
      <Span radius={radius - thickness} fontSize={fontSize} fontColor={fontColor}>
        {target}
        <Percent percentSize={percentSize}>%</Percent>
      </Span>
    </Container>
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
  fontSize: '2.4rem',
  percentSize: '1.2rem',
  radius: 50,
  thickness: 16,
};

export default DonutChart;
