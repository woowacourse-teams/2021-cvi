import styled from '@emotion/styled';
import { Link, NavLink } from 'react-router-dom';
import { FONT_COLOR, PALETTE } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 6rem;
  width: 100%;
  padding-left: 1.2rem;

  & > *:last-child {
    margin-top: 22rem;
  }
`;

const LogoContainer = styled(Link)`
  margin: 0 auto;
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 12rem;
`;

const NavLinkElement = styled(NavLink)`
  display: flex;
  align-items: center;
  padding-left: 6.4rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.PURPLE_GRAY};
  width: 100%;
  height: 5.2rem;
`;
const LogoutButton = styled.button`
  display: flex;
  font-size: 1.6rem;
  align-items: center;
  padding-left: 6.4rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.PURPLE_GRAY};
  width: 100%;
  height: 5.2rem;
`;

const selectedNavStyles = {
  backgroundColor: PALETTE.NAVY100,
  color: FONT_COLOR.BLACK,
  borderTopLeftRadius: '1.6rem',
  borderBottomLeftRadius: '1.6rem',
  border: '0.1rem solid transparent',
};

export { Container, LogoContainer, MenuContainer, NavLinkElement, LogoutButton, selectedNavStyles };
