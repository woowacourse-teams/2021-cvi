import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../constants';

const Container = styled.div`
  margin-top: 4rem;
  padding: 2.5rem 8rem 6rem 8rem;
`;

const FrameContent = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 60rem;
`;

const ButtonContainer = styled.div`
  padding: 2rem 2rem 0 1rem;
`;

const Info = styled.div`
  padding: 2rem 3rem 2rem 3rem;
  display: flex;
  flex-direction: column;
  border-bottom: 0.15rem solid ${PALETTE.NAVY100};
`;

const VaccinationInfo = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 3rem;
`;

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
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
`;
const Content = styled.div`
  padding: 2rem 3rem;
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
export {
  Container,
  FrameContent,
  ButtonContainer,
  Info,
  VaccinationInfo,
  WriterInfo,
  ReviewInfo,
  ShotVerified,
  Writer,
  InfoBottom,
  UpdateButtonContainer,
  CreatedAt,
  Content,
  ViewCount,
  Error,
  buttonStyles,
};
