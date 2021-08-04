import { useState } from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { ALERT_MESSAGE, RESPONSE_STATE, SNACKBAR_MESSAGE, VACCINATION } from '../../constants';
import { Container, TextArea, ButtonWrapper, buttonStyles } from './ReviewWritingModal.styles';
import { BUTTON_SIZE_TYPE } from '../common/Button/Button.styles';
import { postReviewAsync } from '../../service';
import { findKey } from '../../utils';
import { Button, Modal, Selection } from '../common';
import { useSnackBar } from '../../hooks';

const ReviewWritingModal = ({ getReviewList, showLoading, onClickClose }) => {
  const accessToken = useSelector((state) => state.authReducer?.accessToken);

  const [selectedVaccine, setSelectedVaccine] = useState('모더나');
  const [content, setContent] = useState('');

  const { openSnackBar } = useSnackBar();

  const vaccinationList = Object.values(VACCINATION);

  const createReview = async () => {
    const vaccinationType = findKey(VACCINATION, selectedVaccine);
    const data = { content, vaccinationType };
    const response = await postReviewAsync(accessToken, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_CREATE_REVIEW);

      return;
    }

    onClickClose();
    openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_REVIEW);

    showLoading();
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
  showLoading: PropTypes.func.isRequired,
  onClickClose: PropTypes.func.isRequired,
};

export default ReviewWritingModal;
