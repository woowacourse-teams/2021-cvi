import { Container, ItemContainer, Name, Bar, Percentage } from './HorizontalBarChart.styles';

// 210913 02:37 업데이트
const symptomList = [
  {
    name: '접종 부위 통증',
    percentage: 69.23,
    barColor: '#018F9C',
  },
  {
    name: '근육통',
    percentage: 38.46,
    barColor: '#00ACC1',
  },
  {
    name: '피로감',
    percentage: 30.77,
    barColor: '#26C6DA',
  },
  {
    name: '발열',
    percentage: 23.08,
    barColor: '#80deea',
  },
  {
    name: '두통',
    percentage: 7.7,
    barColor: '#b2ebf2',
  },
];

const HorizontalBarChart = () => (
  <Container>
    {symptomList.map((symptom, index) => (
      <ItemContainer key={symptom.name}>
        <Name>{symptom.name}</Name>
        <Bar index={index} percentage={symptom.percentage} color={symptom.barColor} />
        <Percentage>{`${symptom.percentage}%`}</Percentage>
      </ItemContainer>
    ))}
  </Container>
);

export default HorizontalBarChart;
