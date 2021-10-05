import { useEffect, useState } from 'react';
import { ButtonLike } from '../components/@common';
import { ALERT_MESSAGE, RESPONSE_STATE } from '../constants';
import { fetchDeleteLike, fetchPostLike } from '../service/fetch';
import customRequest from '../service/customRequest';
import { useMovePage } from '.';

const useLike = (accessToken, hasLiked, likeCount, postId) => {
  const [updatedHasLiked, setUpdatedHasLiked] = useState(hasLiked);
  const [updatedLikeCount, setUpdatedLikeCount] = useState(likeCount);

  const { goLoginPage } = useMovePage();

  useEffect(() => {
    setUpdatedHasLiked(hasLiked);
    setUpdatedLikeCount(likeCount);
  }, [hasLiked, likeCount]);

  const deleteLike = async () => {
    const response = await customRequest(() => fetchDeleteLike(accessToken, postId));

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    setUpdatedHasLiked(false);
    setUpdatedLikeCount((prevState) => prevState - 1);
  };

  const createLike = async () => {
    const response = await customRequest(() => fetchPostLike(accessToken, postId));

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
      goLoginPage();

      return;
    }

    updatedHasLiked ? deleteLike() : createLike();
  };

  return { onClickLike, ButtonLike, updatedHasLiked, updatedLikeCount, deleteLike };
};

export default useLike;
