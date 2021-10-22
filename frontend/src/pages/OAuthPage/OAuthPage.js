import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation } from 'react-router-dom';
import customRequest from '../../service/customRequest';
import {
  ALERT_MESSAGE,
  LOCAL_STORAGE_KEY,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
} from '../../constants';
import { useMovePage, useSnackbar } from '../../hooks';
import { getMyInfoAsync } from '../../redux/authSlice';
import { fetchPostOAuthLogin } from '../../service/fetch';

const OAuthPage = () => {
  const location = useLocation();
  const dispatch = useDispatch();

  const { openSnackbar } = useSnackbar();
  const { goHomePage, goSignupPage } = useMovePage();

  const login = async () => {
    const code = new URLSearchParams(location.search).get('code');
    const state = new URLSearchParams(location.search).get('state');

    const data = {
      provider: location.pathname.includes('kakao') ? 'KAKAO' : 'NAVER',
      code,
      state,
    };

    const response = await customRequest(() => fetchPostOAuthLogin(data));

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    if (!response.data.accessToken) {
      const { socialProvider, socialId, socialProfileUrl } = response.data;

      goSignupPage({
        socialProvider,
        socialId,
        socialProfileUrl,
      });
    } else {
      const accessToken = response.data.accessToken;

      localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(accessToken));
      dispatch(getMyInfoAsync(accessToken));

      openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_LOGIN);
      goHomePage();
    }
  };

  useEffect(() => {
    login();
  }, []);

  return <></>;
};

export default OAuthPage;
