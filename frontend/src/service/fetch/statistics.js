import { BASE_URL } from '../../constants';

const fetchGetVaccinationStateList = () => fetch(`${BASE_URL}/publicdata/vaccinations`);

const fetchGetWorldVaccinationStateList = () => fetch(`${BASE_URL}/publicdata/vaccinations/world`);

export { fetchGetVaccinationStateList, fetchGetWorldVaccinationStateList };
