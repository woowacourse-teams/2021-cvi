import Frame from '../Frame/Frame';
import HomeIcon from '../../assets/icons/home.svg';
import ReviewIcon from '../../assets/icons/review.svg';
import LoginIcon from '../../assets/icons/login.svg';
import LogoutIcon from '../../assets/icons/logout.svg';
import {
  Container,
  LogoContainer,
  MenuContainer,
  NavLinkElement,
  LogoutButton,
  selectedNavStyles,
} from './SideBar.styles';
import { FONT_COLOR, LOCAL_STORAGE_KEY, PATH } from '../../constants';
import { useSelector, useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { getMyInfoAsync, logout as logoutAction } from '../../redux/authSlice';

const SideBar = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer?.user);
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    dispatch(logoutAction());
  };

  useEffect(() => {
    if (accessToken) {
      dispatch(getMyInfoAsync(accessToken));
    }
  }, [accessToken]);

  return (
    <Frame width="24rem" height="inherit">
      <Container>
        <LogoContainer to={PATH.HOME}>
          {/* example logo */}
          <HomeIcon width="80" height="80" stroke={FONT_COLOR.PURPLE_GRAY} />
        </LogoContainer>
        <MenuContainer>
          <NavLinkElement exact to={PATH.HOME} activeStyle={selectedNavStyles}>
            <HomeIcon width="20" height="20" stroke={FONT_COLOR.PURPLE_GRAY} /> 홈
          </NavLinkElement>
          <NavLinkElement to={PATH.REVIEW} activeStyle={selectedNavStyles}>
            <ReviewIcon width="20" height="20" stroke={FONT_COLOR.PURPLE_GRAY} /> 접종후기
          </NavLinkElement>
        </MenuContainer>

        {Object.keys(user).length > 0 ? (
          <LogoutButton onClick={logout}>
            <LogoutIcon width="24" height="24" stroke={FONT_COLOR.PURPLE_GRAY} /> 로그아웃
          </LogoutButton>
        ) : (
          <NavLinkElement to={PATH.LOGIN} activeStyle={selectedNavStyles}>
            <LoginIcon width="24" height="24" stroke={FONT_COLOR.PURPLE_GRAY} /> 로그인
          </NavLinkElement>
        )}
      </Container>
    </Frame>
  );
};

export default SideBar;
