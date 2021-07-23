import { RESPONSE_STATE } from '../constants';
import {
  requestCreateReview,
  requestDeleteReview,
  requestgetAllReviewList,
  requestgetSelectedReviewList,
  requestPostOAuthLogin,
  requestPostSignup,
  requestPutReview,
} from '../requests';

const getAllReviewListAsync = async () => {
  try {
    const response = await requestgetAllReviewList();

    if (!response.ok) {
      throw new Error(response.status);
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(error);
    // error code 별로 관리
    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const getSelectedReviewListAsync = async (selectedVaccination) => {
  try {
    const response = await requestgetSelectedReviewList(selectedVaccination);

    if (!response.ok) {
      throw new Error(response.status);
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postReviewAsync = async (accessToken, data) => {
  try {
    const response = await requestCreateReview(accessToken, data);

    if (!response.ok) {
      throw new Error(response);
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const putReviewAsync = async (accessToken, id, data) => {
  try {
    const response = await requestPutReview(accessToken, id, data);

    if (!response.ok) {
      throw new Error();
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const deleteReviewAsync = async (accessToken, id) => {
  try {
    const response = await requestDeleteReview(accessToken, id);

    if (!response.ok) {
      throw new Error();
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postSignupAsync = async (data) => {
  try {
    const response = await requestPostSignup(data);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    // const errorResponse = JSON.parse(error.message).message;
    // alert(errorResponse);
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postOAuthLoginAsync = async (data) => {
  try {
    const response = await requestPostOAuthLogin(data);

    if (!response.ok) {
      throw new Error(response);
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

export {
  getAllReviewListAsync,
  getSelectedReviewListAsync,
  postReviewAsync,
  putReviewAsync,
  deleteReviewAsync,
  postSignupAsync,
  postOAuthLoginAsync,
};
