import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useSnackbar } from 'notistack';
import HomeIcon from '../../assets/icons/home.svg';
import ReviewIcon from '../../assets/icons/review.svg';
import LoginIcon from '../../assets/icons/login.svg';
import LogoutIcon from '../../assets/icons/logout.svg';
import LogoIcon from '../../assets/icons/logo.svg';
import {
  Container,
  LogoContainer,
  MenuContainer,
  NavLinkElement,
  LogoutButton,
  selectedNavStyles,
} from './SideBar.styles';
import {
  FONT_COLOR,
  LOCAL_STORAGE_KEY,
  PATH,
  SNACKBAR_MESSAGE,
  THEME_COLOR,
} from '../../constants';
import { getMyInfoAsync, logout as logoutAction } from '../../redux/authSlice';

const SideBar = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer?.user);

  const { enqueueSnackbar } = useSnackbar();

  const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    dispatch(logoutAction());

    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_LOGOUT);
  };

  useEffect(() => {
    const accessToken = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN));

    dispatch(getMyInfoAsync(accessToken));
  }, []);

  return (
    <Container>
      <LogoContainer to={PATH.HOME}>
        <LogoIcon width="112" fill={THEME_COLOR.WHITE} />
      </LogoContainer>
      <MenuContainer>
        <NavLinkElement exact to={PATH.HOME} activeStyle={selectedNavStyles}>
          <HomeIcon width="20" height="20" stroke={FONT_COLOR.WHITE} /> 홈
        </NavLinkElement>
        <NavLinkElement to={PATH.REVIEW} activeStyle={selectedNavStyles}>
          <ReviewIcon width="20" height="20" stroke={FONT_COLOR.WHITE} /> 접종후기
        </NavLinkElement>
      </MenuContainer>

      {Object.keys(user).length > 0 ? (
        <LogoutButton onClick={logout}>
          <LogoutIcon width="24" height="24" stroke={FONT_COLOR.WHITE} /> 로그아웃
        </LogoutButton>
      ) : (
        <NavLinkElement to={PATH.LOGIN} activeStyle={selectedNavStyles}>
          <LoginIcon width="24" height="24" stroke={FONT_COLOR.WHITE} /> 로그인
        </NavLinkElement>
      )}
    </Container>
  );
};

export default SideBar;
