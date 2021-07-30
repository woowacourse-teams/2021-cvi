import { css } from '@emotion/react';
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

const UpdateButtonContainer = styled.div`
  display: flex;
`;

const Info = styled.div`
  display: flex;
  flex-direction: column;
  font-size: 1.2rem;
  margin-right: auto;
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

const TextArea = styled.textarea`
  padding: 1rem 1.5rem;
`;

const buttonStyles = css`
  padding-right: 0;
`;

export {
  Container,
  InfoContainer,
  UpdateButtonContainer,
  Info,
  Writer,
  CreatedAt,
  ShotVerified,
  Content,
  TextArea,
  buttonStyles,
};
