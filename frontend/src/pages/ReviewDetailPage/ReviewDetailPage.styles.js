import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../constants';

const Container = styled.div`
  margin-top: 4rem;
  padding: 2.5rem 8rem 6rem 8rem;

  @media screen and (max-width: 1024px) {
    padding: 0;
    margin-top: 0;
  }
`;

const FrameContent = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 60rem;
  position: relative;
`;

const ButtonContainer = styled.div`
  padding: 2rem 2rem 0 1rem;

  @media screen and (max-width: 1024px) {
    padding: 2rem 0;
  }
`;

const TopContainer = styled.div`
  padding: 2rem 3rem 2rem 3rem;
  display: flex;
  flex-direction: column;
  border-bottom: 0.15rem solid ${PALETTE.NAVY100};

  @media screen and (max-width: 1024px) {
    padding: 2rem;
  }
`;

const VaccinationInfo = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 3rem;
`;

const WriterInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 0.6rem;
`;

const ReviewInfo = styled.div`
  display: flex;
  align-items: center;
  flex-direction: row;
  margin-top: 1rem;
`;

const Writer = styled.div`
  font-size: 2rem;
  font-weight: 600;
`;

const UpdateButtonContainer = styled.div`
  display: flex;
`;

const InfoBottom = styled.div`
  display: flex;
  justify-content: space-between;
`;

const CreatedAt = styled.div`
  font-size: 1.2rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
  padding-bottom: 0.2rem;
  margin: 0 1.6rem 0 0.3rem;
  white-space: nowrap;
`;

const Content = styled.div`
  min-height: 30rem;
  padding: 2rem 3rem;
  white-space: pre-wrap;

  @media screen and (max-width: 1024px) {
    padding: 2rem;
  }
`;

const ViewCount = styled.div`
  font-size: 1.2rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
  padding-bottom: 0.2rem;
  margin-left: 0.3rem;
`;

const Error = styled.div`
  font-size: 2rem;
  display: flex;
  justify-content: center;
  padding-top: 14rem;
  height: 36rem;
`;

const buttonStyles = css`
  padding-right: 0;
`;

const IconContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 0.6rem;
  height: 3.6rem;
`;

const BottomContainer = styled.div`
  display: flex;
  padding: 2rem 3rem;
  gap: 2rem;
  align-items: flex-end;

  @media screen and (max-width: 1024px) {
    padding: 2rem;
  }
`;

const ImageContainer = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 68rem;
  margin: 3rem auto 0 auto;
  align-items: center;
`;

export {
  Container,
  FrameContent,
  ButtonContainer,
  TopContainer,
  VaccinationInfo,
  WriterInfo,
  ReviewInfo,
  Writer,
  InfoBottom,
  UpdateButtonContainer,
  CreatedAt,
  Content,
  ViewCount,
  Error,
  buttonStyles,
  IconContainer,
  BottomContainer,
  ImageContainer,
};
