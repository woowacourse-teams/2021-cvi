import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../constants';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin: 2rem 0 0 0;
  width: 100%;
`;

const TextArea = styled.textarea`
  width: 100%;
  margin: 2.4rem 0;
  height: 36rem;
  border: 0.1rem solid ${FONT_COLOR.BLUE_GRAY};
  border-radius: 1.6rem;
  font-size: 1.6rem;
  padding: 2rem;
  resize: none;
  color: ${FONT_COLOR.BLACK};
`;

const ButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
`;

const buttonStyles = css`
  width: 100%;
  height: 4.4rem;
  border-radius: 1.6rem;
`;

const inputStyles = css`
  display: none;
`;

const labelStyles = css`
  display: block;
  width: 3rem;
  height: 3rem;
  font-size: 2rem;
  font-weight: 100;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  cursor: pointer;
  color: ${FONT_COLOR.WHITE};
  padding: 0 0.3rem 0.1rem 0;
  margin-left: 1.6rem;
  background-color: ${FONT_COLOR.BLUE_GRAY};
`;

const PreviewImage = styled.img`
  width: 6.8rem;
  height: 6.8rem;
  border-radius: 0.6rem;
  box-shadow: 0 0 10px 4px rgba(0, 0, 0, 0.04);
  object-fit: cover;
`;

const PreviewImageContainer = styled.div`
  display: flex;

  & > *:not(:last-child) {
    margin-right: 1rem;
  }
`;

const FileUploadContainer = styled.div`
  border: 0.1rem solid ${FONT_COLOR.BLUE_GRAY};
  margin: 0 0 2rem 0;
  border-radius: 1.6rem;
  height: 8rem;
  display: flex;
  align-items: center;
  padding-left: 1rem;
`;

const FileUploadTitle = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.BLUE_GRAY};
  margin-bottom: 0.4rem;
`;

export {
  Container,
  TextArea,
  ButtonWrapper,
  buttonStyles,
  inputStyles,
  labelStyles,
  PreviewImage,
  PreviewImageContainer,
  FileUploadContainer,
  FileUploadTitle,
};
