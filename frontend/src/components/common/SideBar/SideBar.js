import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';

import {
  Container,
  LogoContainer,
  MenuContainer,
  NavLinkElement,
  LogoutButton,
  selectedNavStyles,
} from './SideBar.styles';
import { PATH, THEME_COLOR } from '../../../constants';
import {
  HomeIcon,
  LoginIcon,
  LogoIcon,
  LogoutIcon,
  ReviewIcon,
  MyPageIcon,
  StateIcon,
} from '../../../assets/icons';

const SideBar = ({ logout }) => {
  const user = useSelector((state) => state.authReducer?.user);

  const isRelatedMyPage = (pathname) =>
    [
      PATH.MY_PAGE,
      PATH.MY_PAGE_ACCOUNT,
      PATH.MY_PAGE_REVIEW,
      PATH.MY_PAGE_COMMENT_REVIEW,
      PATH.MY_PAGE_LIKE_REVIEW,
    ].includes(pathname);

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
        <NavLinkElement to={PATH.STATE} activeStyle={selectedNavStyles}>
          <StateIcon width="20" height="20" stroke="currentColor" /> 접종현황
        </NavLinkElement>
        {!!Object.keys(user).length && (
          <NavLinkElement
            to={PATH.MY_PAGE_ACCOUNT}
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

SideBar.propTypes = { logout: PropTypes.func.isRequired };

export default SideBar;
