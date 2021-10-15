import { fetchPostOAuthLogin, fetchPostSignup } from './auth';
import {
  fetchGetCommentList,
  fetchPostComment,
  fetchPutComment,
  fetchDeleteComment,
} from './comment';
import { fetchPostLike, fetchDeleteLike } from './like';
import { fetchGetMyInfo, fetchPutMyInfo } from './myInfo';
import {
  fetchGetAllReviewList,
  fetchGetSelectedReviewList,
  fetchGetReview,
  fetchPostReview,
  fetchDeleteReview,
  fetchPutReview,
  fetchGetMyReviewList,
  fetchGetMyCommentReviewList,
  fetchGetMyLikeReviewList,
  fetchGetImage,
} from './review';
import { fetchGetVaccinationStateList, fetchGetWorldVaccinationStateList } from './statistics';

export {
  fetchPostOAuthLogin,
  fetchPostSignup,
  fetchGetCommentList,
  fetchPostComment,
  fetchPutComment,
  fetchDeleteComment,
  fetchPostLike,
  fetchDeleteLike,
  fetchGetMyInfo,
  fetchPutMyInfo,
  fetchGetAllReviewList,
  fetchGetSelectedReviewList,
  fetchGetReview,
  fetchPostReview,
  fetchDeleteReview,
  fetchPutReview,
  fetchGetMyReviewList,
  fetchGetMyCommentReviewList,
  fetchGetMyLikeReviewList,
  fetchGetImage,
  fetchGetVaccinationStateList,
  fetchGetWorldVaccinationStateList,
};
