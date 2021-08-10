import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { THEME_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  @media screen and (max-width: 1024px) {
    gap: 2rem;
  }
`;

const Img = styled.img`
  width: 96%;
  height: auto;
  border-radius: 1.6rem;
  margin-top: 1rem;
`;

const buttonStyles = css`
  width: 100%;
  height: 5.8rem;
  border-radius: 1.6rem;
  background-color: ${THEME_COLOR.WHITE};
  border: transparent;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
`;

const Title = styled.h3`
  margin-bottom: 1rem;

  @media screen and (max-width: 801px) {
    margin: 1rem 1.4rem;
  }
`;

const Calendar = styled.div`
  height: fit-content;
`;

const A = styled.a`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 5.8rem;
  background-color: ${THEME_COLOR.WHITE};
  color: ${THEME_COLOR.PRIMARY};
  border-radius: 1.6rem;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
  cursor: pointer;

  &:hover {
    background-color: rgba(70, 159, 171, 0.08);
  }
`;

export { Container, Img, buttonStyles, Title, Calendar, A };
