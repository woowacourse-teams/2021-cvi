import { RESPONSE_STATE } from '../constants';
import {
  requestCreateReview,
  requestGetAllReviewList,
  requestGetSelectedReviewList,
} from '../requests';

const getAllReviewList = async () => {
  try {
    const response = await requestGetAllReviewList();

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

const getSelectedReviewList = async (selectedVaccination) => {
  try {
    const response = await requestGetSelectedReviewList(selectedVaccination);

    if (!response.ok) {
      throw new Error(response.status);
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postReview = async (accessToken, data) => {
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

export { getAllReviewList, getSelectedReviewList, postReview };
