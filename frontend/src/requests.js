import { BASE_URL, FILTER_TYPE, PAGING_SIZE, SORT_TYPE } from './constants';

const getFilterQuery = (filteringList) => {
  const [filterType, sortType] = filteringList;

  if (filterType === FILTER_TYPE.CREATED_AT && sortType === SORT_TYPE.ASC) {
    return 'CREATED_AT_ASC';
  } else if (filterType === FILTER_TYPE.CREATED_AT && sortType === SORT_TYPE.DESC) {
    return 'CREATED_AT_DESC';
  } else if (filterType === FILTER_TYPE.LIKE_COUNT && sortType === SORT_TYPE.ASC) {
    return 'LIKE_COUNT_ASC';
  } else if (filterType === FILTER_TYPE.LIKE_COUNT && sortType === SORT_TYPE.DESC) {
    return 'LIKE_COUNT_DESC';
  } else if (filterType === FILTER_TYPE.VIEW_COUNT && sortType === SORT_TYPE.ASC) {
    return 'VIEW_COUNT_ASC';
  } else if (filterType === FILTER_TYPE.VIEW_COUNT && sortType === SORT_TYPE.DESC) {
    return 'VIEW_COUNT_DESC';
  } else if (filterType === FILTER_TYPE.COMMENT_COUNT && sortType === SORT_TYPE.ASC) {
    return 'COMMENT_COUNT_ASC';
  } else if (filterType === FILTER_TYPE.COMMENT_COUNT && sortType === SORT_TYPE.DESC) {
    return 'COMMENT_COUNT_DESC';
  }
};

const filterQuery = (filteringList) =>
  filteringList ? `&sort=${getFilterQuery(filteringList)}` : '';

const requestGetAllReviewList = (accessToken, offset, filteringList) =>
  fetch(
    `${BASE_URL}/posts/paging?offset=${offset}&size=${PAGING_SIZE}${filterQuery(filteringList)}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

const requestGetSelectedReviewList = (accessToken, vaccinationType, offset, filteringList) =>
  fetch(
    `${BASE_URL}/posts/paging?vaccinationType=${vaccinationType}&offset=${offset}&size=${PAGING_SIZE}${filterQuery(
      filteringList,
    )}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

const requestGetReview = (accessToken, id) =>
  fetch(`${BASE_URL}/posts/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestCreateReview = (accessToken, data) =>
  fetch(`${BASE_URL}/posts`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestDeleteReview = (accessToken, reviewId) =>
  fetch(`${BASE_URL}/posts/${reviewId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestPutReview = (accessToken, reviewId, data) =>
  fetch(`${BASE_URL}/posts/${reviewId}`, {
    method: 'PUT',
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

// users -> users/me
const requestPutAccount = (accessToken, data) =>
  fetch(`${BASE_URL}/users/me`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestCreateComment = (accessToken, reviewId, data) =>
  fetch(`${BASE_URL}/posts/${reviewId}/comments`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`, // 여기 잘못되면 'id는 null이 될 수 없습니다' 오는데 그게 맞는지?
    },
    body: JSON.stringify(data),
  });

const requestPutComment = (accessToken, reviewId, commentId, data) =>
  fetch(`${BASE_URL}/posts/${reviewId}/comments/${commentId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestDeleteComment = (accessToken, reviewId, commentId) =>
  fetch(`${BASE_URL}/posts/${reviewId}/comments/${commentId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestPostLike = (accessToken, postId) =>
  fetch(`${BASE_URL}/posts/${postId}/likes`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestDeleteLike = (accessToken, postId) =>
  fetch(`${BASE_URL}/posts/${postId}/likes`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestVaccinationStateList = () => fetch(`${BASE_URL}/publicdata/vaccinations`);

const requestWorldVaccinationStateList = () => fetch(`${BASE_URL}/publicdata/vaccinations/world`);

const requestGetMyReviewList = (accessToken, offset) =>
  fetch(`${BASE_URL}/users/me/posts/paging?filter=NONE&offset=${offset}&size=${PAGING_SIZE}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestGetMyCommentReviewList = (accessToken, offset) =>
  fetch(`${BASE_URL}/users/me/posts/paging?filter=COMMENTS&offset=${offset}&size=${PAGING_SIZE}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestGetMyLikeReviewList = (accessToken, offset) =>
  fetch(`${BASE_URL}/users/me/posts/paging?filter=LIKES&offset=${offset}&size=${PAGING_SIZE}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestGetCommentList = (postId, offset) =>
  fetch(`${BASE_URL}/posts/${postId}/comments/paging?offset=${offset}&size=${PAGING_SIZE}`);

export {
  requestGetAllReviewList,
  requestGetSelectedReviewList,
  requestGetReview,
  requestCreateReview,
  requestDeleteReview,
  requestPutReview,
  requestPostSignup,
  requestPostOAuthLogin,
  requestGetMyInfo,
  requestPutAccount,
  requestCreateComment,
  requestPutComment,
  requestDeleteComment,
  requestPostLike,
  requestDeleteLike,
  requestVaccinationStateList,
  requestWorldVaccinationStateList,
  requestGetMyReviewList,
  requestGetMyCommentReviewList,
  requestGetMyLikeReviewList,
  requestGetCommentList,
};
