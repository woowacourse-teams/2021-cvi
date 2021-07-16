import PropTypes from 'prop-types';
import Avatar from '../Avatar/Avatar';
import SideBar from '../SideBar/SideBar';
import EllipsisIcon from '../../assets/icons/ellipsis.svg';
import { Background, Container, MainContainer, TopContainer } from './BaseLayout.styles';
import { FONT_COLOR } from '../../constants';
import { useSelector } from 'react-redux';

const BaseLayout = ({ children }) => {
  const isLogin = useSelector((state) => state.authReducer.accessToken);

  return (
    <Background>
      <Container>
        <SideBar />
        <MainContainer>
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
          {children}
        </MainContainer>
      </Container>
    </Background>
  );
};

BaseLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default BaseLayout;
