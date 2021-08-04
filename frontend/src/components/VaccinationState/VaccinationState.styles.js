import styled from '@emotion/styled';
import { FONT_COLOR } from '../../constants';

const Container = styled.div`
  margin-top: 0.3rem;
`;

const Title = styled.h3`
  margin-bottom: 1rem;
`;

const FrameContent = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: repeat(3, 1fr);
  margin: 2.4rem 2.7rem;
`;

const PrimaryState = styled.div`
  display: flex;
  margin-right: auto;
`;

const Info = styled.div`
  color: ${FONT_COLOR.GRAY};
  margin: 1.2rem 0 1.2rem 2.4rem;
  font-size: 1.4rem;
  font-weight: 500;
`;

const InfoTitle = styled.div`
  color: ${FONT_COLOR.BLACK};
  font-weight: 600;
  margin-bottom: 1rem;
  font-size: 1.6rem;
`;

export { Container, Title, FrameContent, PrimaryState, Info, InfoTitle };
