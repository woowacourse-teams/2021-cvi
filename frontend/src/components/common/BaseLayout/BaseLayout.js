import PropTypes from 'prop-types';
import Avatar from '../Avatar/Avatar';
import SideBar from '../SideBar/SideBar';
import { Container, MainContainer, TopContainer } from './BaseLayout.styles';
import { useSelector } from 'react-redux';
import { EllipsisIcon } from '../../../assets/icons';
import { FONT_COLOR, PATH } from '../../../constants';
import { useLocation } from 'react-router-dom';

const BaseLayout = ({ children }) => {
  const location = useLocation();
  const isLogin = !!useSelector((state) => state.authReducer.accessToken);

  return (
    <Container>
      <SideBar />
      <MainContainer>
        {!location.pathname.includes(PATH.MY_PAGE) && (
          <TopContainer>
            {isLogin ? (
              <>
                <Avatar />
                <EllipsisIcon width="24" hight="24" fill={FONT_COLOR.BLACK} />{' '}
              </>
            ) : (
              ''
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
