const getStatisticalValues = (array) => {
  const n = array.length;
  const mean = array.reduce((sum, cur) => sum + cur, 0) / n;
  const standardDeviation = Math.sqrt(
    array.map((x) => Math.pow(x - mean, 2)).reduce((sum, cur) => sum + cur, 0) / n,
  );

  const roundedMean = Math.round(mean * 10) / 10;
  const roundedStandardDeviation = Math.round(standardDeviation * 10) / 10;

  return { n, mean: roundedMean, standardDeviation: roundedStandardDeviation };
};

export default getStatisticalValues;
