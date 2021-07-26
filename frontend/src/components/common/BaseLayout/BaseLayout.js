import PropTypes from 'prop-types';
import Avatar from '../Avatar/Avatar';
import SideBar from '../SideBar/SideBar';
import { Container, MainContainer, TopContainer, avatarStyles } from './BaseLayout.styles';
import { useSelector } from 'react-redux';
import { EllipsisIcon } from '../../../assets/icons';
import { FONT_COLOR, PATH } from '../../../constants';
import { useHistory, useLocation } from 'react-router-dom';

const BaseLayout = ({ children }) => {
  const history = useHistory();
  const location = useLocation();
  const isLogin = !!useSelector((state) => state.authReducer.accessToken);
  const user = useSelector((state) => state.authReducer.user);

  const goMyPage = () => {
    history.push(PATH.MY_PAGE_SHOT_VERIFICATION);
  };

  return (
    <Container>
      <SideBar />
      <MainContainer>
        {!location.pathname.includes(PATH.MY_PAGE) && (
          <TopContainer>
            {isLogin && (
              <>
                <Avatar src={user.socialProfileUrl} styles={avatarStyles} onClick={goMyPage} />
                <EllipsisIcon width="24" hight="24" fill={FONT_COLOR.BLACK} />
              </>
            )}
          </TopContainer>
        )}
        {children}
      </MainContainer>
    </Container>
  );
};

BaseLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default BaseLayout;
