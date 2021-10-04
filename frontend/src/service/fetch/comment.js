import { BASE_URL, PAGING_SIZE } from '../../constants';

const fetchGetCommentList = (postId, offset) =>
  fetch(`${BASE_URL}/posts/${postId}/comments/paging?offset=${offset}&size=${PAGING_SIZE}`);

const fetchPostComment = (accessToken, reviewId, data) =>
  fetch(`${BASE_URL}/posts/${reviewId}/comments`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`, // 여기 잘못되면 'id는 null이 될 수 없습니다' 오는데 그게 맞는지?
    },
    body: JSON.stringify(data),
  });

const fetchPutComment = (accessToken, reviewId, commentId, data) =>
  fetch(`${BASE_URL}/posts/${reviewId}/comments/${commentId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const fetchDeleteComment = (accessToken, reviewId, commentId) =>
  fetch(`${BASE_URL}/posts/${reviewId}/comments/${commentId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export { fetchGetCommentList, fetchPostComment, fetchPutComment, fetchDeleteComment };
