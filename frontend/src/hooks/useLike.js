import { useHistory } from 'react-router-dom';
import { ButtonLike } from '../components/common';
import { ALERT_MESSAGE, PATH, RESPONSE_STATE } from '../constants';
import { deleteLikeAsync, postLikeAsync } from '../service';

const useLike = (accessToken, hasLiked, postId, callback) => {
  const history = useHistory();

  const deleteLike = async () => {
    const response = await deleteLikeAsync(accessToken, postId);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    callback();
  };

  const createLike = async () => {
    const response = await postLikeAsync(accessToken, postId);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    callback();
  };

  const onClickLike = async (event) => {
    event.stopPropagation();

    if (!accessToken) {
      alert(ALERT_MESSAGE.NEED_LOGIN);
      history.push(PATH.LOGIN);

      return;
    }

    hasLiked ? deleteLike() : createLike();
  };

  return { onClickLike, ButtonLike };
};

export default useLike;
