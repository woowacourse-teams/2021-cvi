import { RESPONSE_STATE } from '../constants';
import {
  requestCreateComment,
  requestCreateReview,
  requestDeleteLike,
  requestDeleteReview,
  requestGetAllReviewList,
  requestGetSelectedReviewList,
  requestPostLike,
  requestPostOAuthLogin,
  requestPostSignup,
  requestPutAccount,
  requestPutReview,
  requestDeleteComment,
  requestGetReview,
  requestPutComment,
} from '../requests';

const getAllReviewListAsync = async (accessToken, lastPostId) => {
  try {
    const response = await requestGetAllReviewList(accessToken, lastPostId);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    // error code 별로 관리
    // console.error(JSON.parse(error.message).message);
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const getSelectedReviewListAsync = async (accessToken, selectedVaccination, lastPostId) => {
  try {
    const response = await requestGetSelectedReviewList(
      accessToken,
      selectedVaccination,
      lastPostId,
    );

    if (!response.ok) {
      // 에러 메시지 넣어달라고 하기
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    // console.error(JSON.parse(error.message).message);
    console.error(error);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const getReviewAsync = async (accessToken, id) => {
  try {
    const response = await requestGetReview(accessToken, id);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postReviewAsync = async (accessToken, data) => {
  try {
    const response = await requestCreateReview(accessToken, data);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const putReviewAsync = async (accessToken, id, data) => {
  try {
    const response = await requestPutReview(accessToken, id, data);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const deleteReviewAsync = async (accessToken, id) => {
  try {
    const response = await requestDeleteReview(accessToken, id);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

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
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postOAuthLoginAsync = async (data) => {
  try {
    const response = await requestPostOAuthLogin(data);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const putAccountAsync = async (accessToken, data) => {
  try {
    const response = await requestPutAccount(accessToken, data);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postCommentAsync = async (accessToken, id, data) => {
  try {
    const response = await requestCreateComment(accessToken, id, data);

    if (!response.ok) {
      throw new Error(await response.text());
    }
    return { state: RESPONSE_STATE.SUCCESS, data: await response.json() };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const postLikeAsync = async (accessToken, postId) => {
  try {
    const response = await requestPostLike(accessToken, postId);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const putCommentAsync = async (accessToken, reviewId, commentId, data) => {
  try {
    const response = await requestPutComment(accessToken, reviewId, commentId, data);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const deleteCommentAsync = async (accessToken, reviewId, commentId) => {
  try {
    const response = await requestDeleteComment(accessToken, reviewId, commentId);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

const deleteLikeAsync = async (accessToken, postId) => {
  try {
    const response = await requestDeleteLike(accessToken, postId);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return { state: RESPONSE_STATE.SUCCESS, data: null };
  } catch (error) {
    console.error(JSON.parse(error.message).message);

    return { state: RESPONSE_STATE.FAILURE, data: error };
  }
};

export {
  getAllReviewListAsync,
  getSelectedReviewListAsync,
  getReviewAsync,
  postReviewAsync,
  putReviewAsync,
  deleteReviewAsync,
  postSignupAsync,
  postOAuthLoginAsync,
  putAccountAsync,
  postCommentAsync,
  deleteCommentAsync,
  postLikeAsync,
  deleteLikeAsync,
  putCommentAsync,
};
