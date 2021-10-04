import { BASE_URL, FILTER_TYPE, PAGING_SIZE, SORT_TYPE } from '../../constants';

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

const fetchGetAllReviewList = (accessToken, offset, filteringList) =>
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

const fetchGetSelectedReviewList = (accessToken, vaccinationType, offset, filteringList) =>
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

const fetchGetReview = (accessToken, id) =>
  fetch(`${BASE_URL}/posts/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const fetchPostReview = (accessToken, data) =>
  fetch(`${BASE_URL}/posts`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const fetchDeleteReview = (accessToken, reviewId) =>
  fetch(`${BASE_URL}/posts/${reviewId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const fetchPutReview = (accessToken, reviewId, data) =>
  fetch(`${BASE_URL}/posts/${reviewId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const fetchGetMyReviewList = (accessToken, offset) =>
  fetch(`${BASE_URL}/users/me/posts/paging?offset=${offset}&size=${PAGING_SIZE}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const fetchGetMyCommentReviewList = (accessToken, offset) =>
  fetch(`${BASE_URL}/users/me/posts/paging?filter=COMMENTS&offset=${offset}&size=${PAGING_SIZE}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const fetchGetMyLikeReviewList = (accessToken, offset) =>
  fetch(`${BASE_URL}/users/me/posts/paging?filter=LIKES&offset=${offset}&size=${PAGING_SIZE}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const fetchGetImage = (image) => fetch(image);

export {
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
};
