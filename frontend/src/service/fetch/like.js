import { BASE_URL } from '../../constants';

const fetchPostLike = (accessToken, postId) =>
  fetch(`${BASE_URL}/posts/${postId}/likes`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const fetchDeleteLike = (accessToken, postId) =>
  fetch(`${BASE_URL}/posts/${postId}/likes`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export { fetchPostLike, fetchDeleteLike };
