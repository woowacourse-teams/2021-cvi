import { BASE_URL } from './constants';

const requestGetReviewList = () => fetch(`${BASE_URL}/posts`);

const requestGetSelectedVaccinationReviewList = (vaccinationType) =>
  fetch(`${BASE_URL}/posts?vaccinationType=${vaccinationType}`);

export { requestGetReviewList, requestGetSelectedVaccinationReviewList };
