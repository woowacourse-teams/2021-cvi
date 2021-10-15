import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Form = styled.form`
  display: flex;
  flex-direction: column;
  margin: 2rem 0 0 0;
  width: 100%;
`;

const TextArea = styled.textarea`
  width: 100%;
  margin: 2.4rem 0 2rem 0;
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
  font-size: 2.4rem;
  font-weight: 100;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  cursor: pointer;
  color: ${FONT_COLOR.WHITE};
  padding: 0.1rem 0.3rem 0 0;
  margin-left: 2.2rem;
  background-color: ${FONT_COLOR.BLUE_GRAY};

  &:hover {
    background-color: #9da5ad;
  }
`;

const PreviewImage = styled.img`
  width: 6rem;
  height: 6rem;
  border-radius: 0.6rem;
  box-shadow: 0 0 10px 4px rgba(0, 0, 0, 0.04);
  object-fit: cover;
`;

const PreviewListContainer = styled.div`
  display: flex;

  & > *:not(:last-child) {
    margin-right: 1rem;
  }
`;

const FileUploadContainer = styled.div`
  border: 0.1rem solid ${FONT_COLOR.BLUE_GRAY};
  margin: 0 0 2rem 0;
  border-radius: 1.6rem;
  min-height: 8.4rem;
  display: flex;
  align-items: center;
  padding: 1rem;
  overflow-x: auto;
`;

const FileUploadTitle = styled.div`
  font-size: 1.4rem;
  color: ${FONT_COLOR.BLUE_GRAY};
  margin-bottom: 0.4rem;
`;

const deleteImageButtonStyles = css`
  position: absolute;
  top: -0.6rem;
  right: -0.6rem;
  width: 1.4rem;
  height: 1.4rem;
  border-radius: 50%;
  font-size: 1.6rem;
  padding: 0 0 0.2rem 0;
  transform: rotate(45deg);
  font-weight: 100;
`;

const PreviewImageContainer = styled.div`
  position: relative;
`;

const CurrentImageCount = styled.span`
  color: ${THEME_COLOR.PRIMARY};
`;

export {
  Form,
  TextArea,
  ButtonWrapper,
  buttonStyles,
  inputStyles,
  labelStyles,
  PreviewImage,
  PreviewListContainer,
  FileUploadContainer,
  FileUploadTitle,
  deleteImageButtonStyles,
  PreviewImageContainer,
  CurrentImageCount,
};
