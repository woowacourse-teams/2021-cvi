import { BASE_URL } from './constants';

const requestGetReviewList = () => fetch(`${BASE_URL}/posts`);

export { requestGetReviewList };
