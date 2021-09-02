import { useState } from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import {
  ALERT_MESSAGE,
  RESPONSE_STATE,
  REVIEW_IMAGE_LIMIT,
  SNACKBAR_MESSAGE,
  VACCINATION,
} from '../../constants';
import {
  Container,
  TextArea,
  ButtonWrapper,
  buttonStyles,
  inputStyles,
  PreviewImage,
  PreviewImageContainer,
  labelStyles,
  FileUploadContainer,
  FileUploadTitle,
} from './ReviewWritingModal.styles';
import { BUTTON_SIZE_TYPE } from '../common/Button/Button.styles';
import { postReviewAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Input, Modal, Selection } from '../common';
import { useSnackBar } from '../../hooks';

const ReviewWritingModal = ({ getReviewList, setReviewList, setOffset, onClickClose }) => {
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [selectedVaccine, setSelectedVaccine] = useState(VACCINATION.MODERNA);
  const [content, setContent] = useState('');
  const [images, setImages] = useState([]);

  const { openSnackBar } = useSnackBar();

  const vaccinationList = Object.values(VACCINATION);

  const updateImageFormat = (image) => {
    const [type, data] = image.split(',');

    const startIndex = type.indexOf('/');
    const endIndex = type.indexOf(';');

    return { type: type.substring(startIndex + 1, endIndex).toUpperCase(), data };
  };

  const uploadImages = (event) => {
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

    files.forEach((file) => {
      const reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onload = () => {
        setImages((prevState) => [...prevState, reader.result]);
      };
    });
  };

  const createReview = async () => {
    const updatedImageFormat = images.map((image) => updateImageFormat(image));
    const vaccinationType = findKey(VACCINATION, selectedVaccine);
    const data = { content, vaccinationType, images: updatedImageFormat };

    const response = await postReviewAsync(accessToken, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_CREATE_REVIEW);

      return;
    }

    onClickClose();
    openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_REVIEW);

    setReviewList([]);
    setOffset(0);
    getReviewList();
  };

  return (
    <Modal showCloseButton={true} showShadow={true} onClickClose={onClickClose}>
      <Container>
        <Selection
          selectionList={vaccinationList}
          selectedItem={selectedVaccine}
          setSelectedItem={setSelectedVaccine}
        />
        <TextArea width="100%" onChange={(event) => setContent(event.target.value)} />
        <FileUploadTitle>사진 첨부</FileUploadTitle>
        <FileUploadContainer>
          <PreviewImageContainer>
            {!!images.length &&
              images.map((image, index) => (
                <PreviewImage key={`preview-image-${index + 1}`} src={image} />
              ))}
          </PreviewImageContainer>
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
          <Button sizeType={BUTTON_SIZE_TYPE.LARGE} styles={buttonStyles} onClick={createReview}>
            제출하기
          </Button>
        </ButtonWrapper>
      </Container>
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
