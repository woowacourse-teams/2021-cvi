const OAuthPage = () => {
  const login = async () => {
    const code = new URLSearchParams(window.location.search).get('code');

    console.log(code);

    // KAKAO
    const data = {
      provider: 'KAKAO',
      code,
      state: '',
    };

    // NAVER
    // const data = {
    //   provider: 'NAVER',
    //   code,
    //   state: '',
    // };

    try {
      // const response = await requestPostLogin(data);
      // success, 이미 우리 회원 -> main page
      // success, 우리 회웡 아님 -> signupPage
    } catch (error) {
      // failure
    }
  };

  login();

  return <></>;
};

export default OAuthPage;
