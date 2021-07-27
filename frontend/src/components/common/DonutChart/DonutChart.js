import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { THEME_COLOR } from '../../../constants';
import { Container, Span } from './DonutChart.styles';

const DonutChart = ({ target, color, radius, thickness, fontSize }) => {
  const [currentNumber, setCurrentNumber] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (currentNumber < target) setCurrentNumber((currentNumber) => currentNumber + 1);
    }, 10);

    return () => clearInterval(intervalId);
  }, [currentNumber]);

  return (
    <Container currentNumber={currentNumber} color={color} radius={radius}>
      <Span radius={radius - thickness} fontSize={fontSize}>
        {target}%
      </Span>
    </Container>
  );
};

DonutChart.propTypes = {
  target: PropTypes.number.isRequired,
  color: PropTypes.string,
  radius: PropTypes.number,
  thickness: PropTypes.number,
  fontSize: PropTypes.string,
};

DonutChart.defaultProps = {
  color: THEME_COLOR.PRIMARY,
  radius: 50,
  thickness: 16,
  fontSize: '2rem',
};

export default DonutChart;
