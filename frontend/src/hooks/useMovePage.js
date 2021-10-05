import { useHistory } from 'react-router-dom';
import { PATH } from '../constants';

const useMovePage = () => {
  const history = useHistory();

  const goSignupPage = (state = {}) =>
    Object.keys(state).length && history.push({ pathname: PATH.SIGNUP, state });

  const goLoginPage = () => history.push(PATH.LOGIN);

  const goHomePage = () => history.push(PATH.HOME);

  const goMyPage = () => history.push(PATH.MY_PAGE_ACCOUNT);

  const goReviewPage = (state = {}) =>
    Object.keys(state).length
      ? history.push({
          pathname: PATH.REVIEW,
          state,
        })
      : history.push(PATH.REVIEW);

  const goReviewDetailPage = (id) => history.push(`${PATH.REVIEW}/${id}`);

  const goReviewEditPage = (id) => history.push(`${PATH.REVIEW}/${id}/edit`);

  const goStatePage = () => history.push(PATH.STATE);

  const goPreviousPage = () => history.goBack();

  return {
    goSignupPage,
    goLoginPage,
    goHomePage,
    goMyPage,
    goReviewPage,
    goReviewDetailPage,
    goReviewEditPage,
    goStatePage,
    goPreviousPage,
  };
};

export default useMovePage;
