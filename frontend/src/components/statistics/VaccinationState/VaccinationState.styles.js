import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../../constants';

const Container = styled.div`
  margin-top: 0.3rem;
  display: grid;
`;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Title = styled.h3`
  margin-bottom: 1rem;

  @media screen and (max-width: 801px) {
    margin: 1rem 1.4rem;
  }
`;

const FrameContent = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: ${({ withWorld }) => (withWorld ? 'repeat(3, 1fr)' : 'repeat(2, 1fr)')};
  margin: 2.4rem 2.7rem;
  padding: 0 0.6rem;
  position: relative;

  @media screen and (max-width: 801px) {
    margin: 2rem 0 6rem 0;
    gap: 1rem;
  }
`;

const PrimaryState = styled.div`
  display: flex;
  margin-right: auto;

  @media screen and (max-width: 801px) {
    margin: 0 auto;
    flex-direction: column;
  }
`;

const Info = styled.div`
  color: ${FONT_COLOR.GRAY};
  margin: 1.2rem 0 1.2rem 2.4rem;
  font-size: 1.4rem;
  font-weight: 500;

  @media screen and (max-width: 801px) {
    margin: 0;
    font-size: 1.2rem;
  }
`;

const InfoTitle = styled.div`
  color: ${FONT_COLOR.BLACK};
  font-weight: 600;
  margin-bottom: 1rem;
  font-size: 1.6rem;

  @media screen and (max-width: 801px) {
    font-size: 1.6rem;
    margin-top: 2rem;
  }
`;

const IncreaseIcon = styled.span`
  color: ${PALETTE.RED500};
  font-size: 1.2rem;
`;

const buttonStyles = css`
  padding-right: 0;

  @media screen and (max-width: 801px) {
    margin: auto 0;
  }
`;

const Source = styled.span`
  position: absolute;
  font-size: 1.2rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
  bottom: -1rem;
  right: 0;
  white-space: nowrap;

  @media screen and (max-width: 801px) {
    left: 50%;
    right: unset;
    transform: translateX(-50%);
    bottom: -5rem;
  }
`;

export {
  Container,
  TopContainer,
  Title,
  FrameContent,
  PrimaryState,
  Info,
  InfoTitle,
  IncreaseIcon,
  buttonStyles,
  Source,
};
