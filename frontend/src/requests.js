import { BASE_URL } from './constants';

const requestGetReviewList = () => fetch(`${BASE_URL}/posts`);

const requestGetSelectedVaccinationReviewList = (vaccinationType) =>
  fetch(`${BASE_URL}/posts?vaccinationType=${vaccinationType}`);

const requestPostSignup = (data) =>
  fetch(`${BASE_URL}/users/signup`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: JSON.stringify(data),
  });

const requestPostLogin = (data) =>
  fetch(`${BASE_URL}/users/signin`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: JSON.stringify(data),
  });

const requestGetMyInfo = (accessToken) =>
  fetch(`${BASE_URL}/users/me`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export {
  requestGetReviewList,
  requestGetSelectedVaccinationReviewList,
  requestPostSignup,
  requestPostLogin,
  requestGetMyInfo,
};
