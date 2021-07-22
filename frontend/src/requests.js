import { BASE_URL } from './constants';

const requestGetAllReviewList = () => fetch(`${BASE_URL}/posts`);

const requestGetSelectedReviewList = (vaccinationType) =>
  fetch(`${BASE_URL}/posts?vaccinationType=${vaccinationType}`);

const requestGetReview = (id) => fetch(`${BASE_URL}/posts/${id}`);

const requestCreateReview = (accessToken, data) =>
  fetch(`${BASE_URL}/posts`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestPostSignup = (data) =>
  fetch(`${BASE_URL}/users/signup`, {
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

const requestPostOAuthLogin = (data) =>
  fetch(`${BASE_URL}/users/auth`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: JSON.stringify(data),
  });

export {
  requestGetAllReviewList,
  requestGetSelectedReviewList,
  requestGetReview,
  requestCreateReview,
  requestPostSignup,
  requestPostOAuthLogin,
  requestGetMyInfo,
};
