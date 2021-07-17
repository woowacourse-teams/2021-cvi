import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../constants';

const Container = styled.div``;

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

const ReviewInfo = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 5.2rem;
`;

const ShotVerified = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GREEN};
`;

const WriterContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 0.6rem;
`;

const Writer = styled.div`
  font-size: 2rem;
  font-weight: 600;
`;

const CreatedAt = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
  margin-top: 0.4rem;
`;
const Content = styled.div`
  padding: 2rem 3rem;
`;

const ViewCount = styled.div`
  margin-top: auto;
  padding: 3rem;
`;

const Error = styled.div`
  font-size: 2rem;
  display: flex;
  justify-content: center;
  padding-top: 14rem;
  height: 36rem;
`;

export {
  Container,
  FrameContent,
  ButtonContainer,
  Info,
  ReviewInfo,
  ShotVerified,
  WriterContainer,
  Writer,
  CreatedAt,
  Content,
  ViewCount,
  Error,
};
