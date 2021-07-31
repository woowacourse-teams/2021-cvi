import styled from '@emotion/styled';
import { THEME_COLOR } from '../../constants';

const Container = styled.div`
  height: 100%;
  display: flex;
`;

const RightSideBar = styled.div`
  background-color: ${THEME_COLOR.WHITE};
  min-width: 28rem;
  height: 100%;
  margin-left: auto;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;

  @media screen and (max-width: 1024px) {
    display: none;
  }
`;

const ProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 50%;
  justify-content: center;
  align-items: center;
`;

const User = styled.div`
  margin-top: 1.2rem;
  font-size: 1.8rem;
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 50%;
`;

const MenuTitle = styled.div`
  font-weight: 600;
  font-size: 1.8rem;
  margin-bottom: 1rem;
`;

const MenuItem = styled.div`
  border-top: 0.2rem solid ${THEME_COLOR.BACKGROUND};
  padding: 2rem 4rem;
`;

export { Container, RightSideBar, ProfileContainer, User, MenuContainer, MenuTitle, MenuItem };
