import styled from '@emotion/styled';
import { Link, NavLink } from 'react-router-dom';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  height: inherit;
  display: flex;
  flex-direction: column;
  padding: 6rem 0;
  width: 28rem;
  background-color: ${THEME_COLOR.PRIMARY};

  & > *:last-child {
    margin-top: auto;
  }

  @media screen and (max-width: 1024px) {
    display: none;
  }
`;

const LogoContainer = styled(Link)`
  margin: 0 auto;
  padding-right: 1.6rem;

  @media screen and (max-width: 1024px) {
    padding: 1rem 0;
  }
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 11rem;
`;

const NavLinkElement = styled(NavLink)`
  display: flex;
  align-items: center;
  padding-left: 7.8rem;
  margin-left: 1rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.WHITE};
  width: 100%;
  height: 5.6rem;
  font-size: 1.8rem;
`;

const LogoutButton = styled.button`
  display: flex;
  font-size: 1.6rem;
  align-items: center;
  margin-left: 1rem;
  padding-left: 7.8rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.WHITE};
  width: 100%;
  height: 5.2rem;
  font-size: 1.8rem;
`;

const selectedNavStyles = {
  backgroundColor: THEME_COLOR.BACKGROUND,
  color: FONT_COLOR.BLACK,
  borderTopLeftRadius: '1.6rem',
  borderBottomLeftRadius: '1.6rem',
  border: 'transparent',
};

export { Container, LogoContainer, MenuContainer, NavLinkElement, LogoutButton, selectedNavStyles };
