import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import Compressor from 'compressorjs';
import {
  ALERT_MESSAGE,
  FONT_COLOR,
  RESPONSE_STATE,
  REVIEW_IMAGE_LIMIT,
  SNACKBAR_MESSAGE,
  VACCINATION,
} from '../../../constants';
import {
  Form,
  TextArea,
  ButtonWrapper,
  buttonStyles,
  inputStyles,
  PreviewImage,
  PreviewListContainer,
  labelStyles,
  FileUploadContainer,
  FileUploadTitle,
  deleteImageButtonStyles,
  PreviewImageContainer,
  CurrentImageCount,
} from './ReviewWritingModal.styles';
import { BUTTON_SIZE_TYPE } from '../../@common/Button/Button.styles';
import { findKey } from '../../../utils';
import { Button, Input, Modal, Selection } from '../../@common';
import { useLoading, useSnackbar } from '../../../hooks';
import { fetchPostReview } from '../../../service/fetch';
import customRequest from '../../../service/customRequest';

const ReviewWritingModal = ({ getReviewList, setReviewList, setOffset, onClickClose }) => {
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [selectedVaccine, setSelectedVaccine] = useState(VACCINATION.MODERNA);
  const [content, setContent] = useState('');
  const [images, setImages] = useState([]);

  const { openSnackbar } = useSnackbar();
  const { showLoading, hideLoading, isLoading, Loading } = useLoading();

  const vaccinationList = Object.values(VACCINATION);

  const updateImageFormat = (image) => {
    const [type, data] = image.split(',');

    const startIndex = type.indexOf('/');
    const endIndex = type.indexOf(';');

    return { type: type.substring(startIndex + 1, endIndex).toUpperCase(), data };
  };

  const uploadImages = async (event) => {
    if (images.length === REVIEW_IMAGE_LIMIT) {
      alert(ALERT_MESSAGE.OVER_IMAGE_COUNT);

      return;
    }

    let files = [...event.target.files];
    const leftImageCount = REVIEW_IMAGE_LIMIT.MAX_COUNT - images.length;

    if (files.length > leftImageCount) {
      files = files.slice(0, leftImageCount);

      alert(ALERT_MESSAGE.OVER_IMAGE_COUNT);
    }

    for (let file of files) {
      if (file.size > REVIEW_IMAGE_LIMIT.MAX_SIZE) {
        alert(ALERT_MESSAGE.OVER_IMAGE_SIZE);

        continue;
      }

      new Compressor(file, {
        maxWidth: 1200,
        quality: 1,
        strict: true,
        success: (result) => {
          const reader = new FileReader();

          reader.readAsDataURL(result);
          reader.onload = () => {
            setImages((prevState) => [...prevState, reader.result]);
          };
        },
        error: (error) => {
          console.log(error.message);
        },
      });
    }
  };

  const deleteImage = (index) => {
    const updatedImages = [...images.slice(0, index), ...images.slice(index + 1, images.length)];
    setImages(updatedImages);
  };

  const createReview = async (event) => {
    event.preventDefault();

    if (!content) {
      alert(ALERT_MESSAGE.NEED_REVIEW_CONTENT);

      return;
    }

    showLoading();

    const updatedImageFormat = images.map((image) => updateImageFormat(image));
    const vaccinationType = findKey(VACCINATION, selectedVaccine);
    const data = { content, vaccinationType, images: updatedImageFormat };

    const response = await customRequest(() => fetchPostReview(accessToken, data));

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_CREATE_REVIEW);
      hideLoading();

      return;
    }

    onClickClose();
    hideLoading();

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_REVIEW);

    setReviewList([]);
    setOffset(0);
    getReviewList();
  };

  useEffect(() => {
    return () => {
      setContent('');
      setImages([]);
    };
  }, []);

  return (
    <Modal showCloseButton={true} showShadow={true} onClickClose={onClickClose}>
      <Form onSubmit={createReview}>
        <Selection
          selectionList={vaccinationList}
          selectedItem={selectedVaccine}
          setSelectedItem={setSelectedVaccine}
        />
        <TextArea width="100%" isRequired onChange={(event) => setContent(event.target.value)} />
        <FileUploadTitle>
          사진 첨부 <CurrentImageCount>{images.length}</CurrentImageCount>
          {`/${REVIEW_IMAGE_LIMIT.MAX_COUNT}`}
        </FileUploadTitle>
        <FileUploadContainer>
          <PreviewListContainer>
            {!!images.length &&
              images.map((image, index) => (
                <PreviewImageContainer key={`preview-image-${index + 1}`}>
                  <Button
                    type="button"
                    color={FONT_COLOR.BLUE_GRAY}
                    styles={deleteImageButtonStyles}
                    onClick={() => deleteImage(index)}
                  >
                    +
                  </Button>
                  <PreviewImage src={image} />
                </PreviewImageContainer>
              ))}
          </PreviewListContainer>
          {images.length < REVIEW_IMAGE_LIMIT.MAX_COUNT && (
            <Input
              type="file"
              name="review-image"
              multiple
              accept="image/jpg, image/png, image/jpeg"
              inputStyles={inputStyles}
              labelStyles={labelStyles}
              labelText="+"
              onChange={uploadImages}
            />
          )}
        </FileUploadContainer>
        <ButtonWrapper>
          <Button sizeType={BUTTON_SIZE_TYPE.LARGE} styles={buttonStyles}>
            제출하기
          </Button>
        </ButtonWrapper>
      </Form>
      {isLoading && <Loading isLoading={isLoading} />}
    </Modal>
  );
};

ReviewWritingModal.propTypes = {
  getReviewList: PropTypes.func.isRequired,
  setReviewList: PropTypes.func.isRequired,
  setOffset: PropTypes.func.isRequired,
  onClickClose: PropTypes.func.isRequired,
};

export default ReviewWritingModal;
