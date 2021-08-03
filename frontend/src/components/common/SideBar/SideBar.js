import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  Container,
  LogoContainer,
  MenuContainer,
  NavLinkElement,
  LogoutButton,
  selectedNavStyles,
} from './SideBar.styles';
import { LOCAL_STORAGE_KEY, PATH, SNACKBAR_MESSAGE, THEME_COLOR } from '../../../constants';
import { getMyInfoAsync, logout as logoutAction } from '../../../redux/authSlice';
import {
  HomeIcon,
  LoginIcon,
  LogoIcon,
  LogoutIcon,
  ReviewIcon,
  MyPageIcon,
} from '../../../assets/icons';
import { useSnackBar } from '../../../hooks';

const SideBar = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer?.user);

  const { openSnackBar } = useSnackBar();

  const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    dispatch(logoutAction());

    openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_LOGOUT);
  };

  const isRelatedMyPage = (pathname) =>
    [
      PATH.MY_PAGE,
      PATH.MY_PAGE_ACCOUNT,
      PATH.MY_PAGE_REVIEWS,
      PATH.MY_PAGE_SHOT_VERIFICATION,
    ].includes(pathname);

  useEffect(() => {
    const accessToken = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN));

    if (!accessToken) return;

    dispatch(getMyInfoAsync(accessToken));
  }, []);

  return (
    <Container>
      <LogoContainer to={PATH.HOME}>
        <LogoIcon width="112" fill={THEME_COLOR.WHITE} />
      </LogoContainer>
      <MenuContainer>
        <NavLinkElement exact to={PATH.HOME} activeStyle={selectedNavStyles}>
          <HomeIcon width="20" height="20" stroke="currentColor" /> 홈
        </NavLinkElement>
        <NavLinkElement to={PATH.REVIEW} activeStyle={selectedNavStyles}>
          <ReviewIcon width="20" height="20" stroke="currentColor" /> 접종후기
        </NavLinkElement>
        {!!Object.keys(user).length && (
          <NavLinkElement
            to={PATH.MY_PAGE_SHOT_VERIFICATION}
            activeStyle={selectedNavStyles}
            isActive={(_, { pathname }) => isRelatedMyPage(pathname)}
          >
            <MyPageIcon width="20" height="20" stroke="currentColor" fill="currentColor" />{' '}
            마이페이지
          </NavLinkElement>
        )}
      </MenuContainer>

      {Object.keys(user).length ? (
        <LogoutButton onClick={logout}>
          <LogoutIcon width="24" height="24" stroke="currentColor" /> 로그아웃
        </LogoutButton>
      ) : (
        <NavLinkElement to={PATH.LOGIN} activeStyle={selectedNavStyles}>
          <LoginIcon width="24" height="24" stroke="currentColor" /> 로그인
        </NavLinkElement>
      )}
    </Container>
  );
};

export default SideBar;
