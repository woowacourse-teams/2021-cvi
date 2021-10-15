import { useEffect } from 'react';
import { RegionalStateChart, RegionalStateTable, VaccinationState } from '../../components';
import { useFetch, useLoading } from '../../hooks';
import { fetchGetVaccinationStateList } from '../../service/fetch';
import { Container, Title, Content, Source } from './StatePage.styles';

const StatePage = () => {
  const { response, error, loading } = useFetch([], fetchGetVaccinationStateList);
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();

  const regionalState = response.slice(1);

  useEffect(() => {
    loading ? showLoading() : hideLoading();
  }, [loading]);

  return (
    <Container>
      <Title>접종 현황</Title>
      <Content>
        <VaccinationState withWorld={false} />
        <RegionalStateChart
          vaccinationStateList={regionalState}
          isLoading={isLoading}
          Loading={Loading}
        />
        <RegionalStateTable vaccinationStateList={regionalState} />
      </Content>
      <Source>출처: 질병관리청</Source>
    </Container>
  );
};

export default StatePage;
