import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const InfoContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.8rem;
`;

const Info = styled.div`
  display: flex;
  flex-direction: column;
  font-size: 1.2rem;
`;

const Writer = styled.div`
  display: flex;
  font-weight: 600;
`;

const CreatedAt = styled.div`
  color: ${FONT_COLOR.LIGHT_GRAY};
`;

const ShotVerified = styled.span`
  font-weight: 500;
  margin-left: 0.8rem;
  color: ${FONT_COLOR.GREEN};
`;

const Content = styled.div`
  font-size: 1.4rem;
`;

export { Container, InfoContainer, Info, Writer, CreatedAt, ShotVerified, Content };
