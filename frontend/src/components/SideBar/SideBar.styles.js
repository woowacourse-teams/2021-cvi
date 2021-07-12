import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 6rem;

  & > *:last-child {
    margin-top: 22rem;
  }
`;

const LogoContainer = styled.div`
  margin: 0 auto;
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 12rem;
  gap: 2.4rem;
`;

const LinkContainer = styled(Link)`
  display: flex;
  align-items: center;
  gap: 0.8rem;
  color: ${FONT_COLOR.PURPLE_GRAY};
`;

export { Container, LogoContainer, MenuContainer, LinkContainer };
