import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, LINE_LIMIT } from '../../../constants';

const Container = styled.li`
  padding: 2.4rem 3.2rem;
  display: flex;
  flex-direction: column;
  cursor: pointer;

  @media screen and (max-width: 801px) {
    padding: 2rem;
  }
`;

const Content = styled.div`
  margin: 2.4rem 0;
  line-height: 1.5;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: ${LINE_LIMIT.REVIEW_ITEM};
  -webkit-box-orient: vertical;
  height: 7.2rem;
  overflow: hidden;
`;

const Writer = styled.div`
  font-size: 1.4rem;
`;

const ViewCount = styled.div`
  margin-left: 0.4rem;
`;

const ContentContainer = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 2rem;
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
  color: ${FONT_COLOR.LIGHT_GRAY};
  font-size: 1.4rem;
  margin-top: 0.6rem;
`;

const InfoContainer = styled.div`
  display: flex;
  gap: 1.6rem;
`;

const IconContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const CreatedAt = styled.div`
  display: flex;
  align-items: center;
  white-space: nowrap;
`;

const buttonStyles = css`
  color: ${FONT_COLOR.LIGHT_GRAY};
  padding-left: 0;
`;

const PreviewImage = styled.img`
  width: 12rem;
  height: 12rem;
`;

const PreviewImageContainer = styled.div`
  width: 12rem;
  height: 12rem;
  position: relative;
`;

const MoreImageCount = styled.div`
  width: 12rem;
  height: 12rem;
  position: absolute;
  top: 0;
  left: 0;
  font-size: 2.8rem;
  color: ${FONT_COLOR.WHITE};
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export {
  Container,
  IconContainer,
  Content,
  Writer,
  ViewCount,
  ContentContainer,
  BottomContainer,
  InfoContainer,
  CreatedAt,
  buttonStyles,
  PreviewImage,
  PreviewImageContainer,
  MoreImageCount,
};
