import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { ButtonLike } from '../components/common';
import { ALERT_MESSAGE, PATH, RESPONSE_STATE } from '../constants';
import { deleteLikeAsync, postLikeAsync } from '../service';

const useLike = (accessToken, hasLiked, likeCount, postId) => {
  const history = useHistory();
  const [updatedHasLiked, setUpdatedHasLiked] = useState(hasLiked);
  const [updatedLikeCount, setUpdatedLikeCount] = useState(likeCount);

  useEffect(() => {
    setUpdatedHasLiked(hasLiked);
    setUpdatedLikeCount(likeCount);
  }, [hasLiked, likeCount]);

  const deleteLike = async () => {
    const response = await deleteLikeAsync(accessToken, postId);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    setUpdatedHasLiked(false);
    setUpdatedLikeCount((prevState) => prevState - 1);
  };

  const createLike = async () => {
    const response = await postLikeAsync(accessToken, postId);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    setUpdatedHasLiked(true);
    setUpdatedLikeCount((prevState) => prevState + 1);
  };

  const onClickLike = async (event) => {
    event.stopPropagation();

    if (!accessToken) {
      alert(ALERT_MESSAGE.NEED_LOGIN);
      history.push(PATH.LOGIN);

      return;
    }

    updatedHasLiked ? deleteLike() : createLike();
  };

  return { onClickLike, ButtonLike, updatedHasLiked, updatedLikeCount };
};

export default useLike;
