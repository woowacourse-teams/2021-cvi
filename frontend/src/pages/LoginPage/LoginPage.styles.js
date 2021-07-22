import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
`;

const Title = styled.div`
  font-size: 2.8rem;
  font-weight: 500;
  margin-bottom: 4.4rem;
`;

const frameStyles = css`
  flex-direction: column;
  padding: 4rem;
`;

const NaverButton = styled.a`
  background-color: #03c75a;
  color: ${FONT_COLOR.WHITE};
  display: flex;
  justify-content: center;
  align-items: center;
  height: 5.2rem;
  border-radius: 2.6rem;
  gap: 1rem;
  margin-bottom: 2rem;

  &:hover {
    background-color: #02b351;
  }
`;

const KakaoButton = styled.a`
  background-color: #fee500;
  color: rgba(0, 0, 0, 0.85);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 5.2rem;
  border-radius: 2.6rem;
  gap: 0.8rem;

  &:hover {
    background-color: #f2da00;
  }
`;

export { Title, Container, frameStyles, KakaoButton, NaverButton };
