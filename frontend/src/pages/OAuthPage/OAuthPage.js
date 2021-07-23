import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import { ALERT_MESSAGE, LOCAL_STORAGE_KEY, PATH, RESPONSE_STATE } from '../../constants';
import { getMyInfoAsync } from '../../redux/authSlice';
import { postOAuthLoginAsync } from '../../service';

const OAuthPage = () => {
  const history = useHistory();
  const location = useLocation();
  const dispatch = useDispatch();

  const login = async () => {
    const code = new URLSearchParams(location.search).get('code');
    const state = new URLSearchParams(location.search).get('state');

    const data = {
      provider: location.pathname.includes('kakao') ? 'KAKAO' : 'NAVER',
      code,
      state,
    };

    const response = await postOAuthLoginAsync(data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_SERVER);

      return;
    }

    if (!response.data.accessToken) {
      const { socialProvider, socialId, socialProfileUrl } = response.data;

      history.push({
        pathname: `${PATH.SIGNUP}`,
        state: {
          socialProvider,
          socialId,
          socialProfileUrl,
        },
      });
    } else {
      const accessToken = response.data.accessToken;

      localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(accessToken));
      dispatch(getMyInfoAsync(accessToken));
      history.push(`${PATH.HOME}`);
    }
  };

  useEffect(() => {
    login();
  }, []);

  return <></>;
};

export default OAuthPage;
