import { useState } from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { useSnackbar } from 'notistack';
import { SNACKBAR_MESSAGE, VACCINATION } from '../../constants';
import Modal from '../Modal/Modal';
import Selection from '../Selection/Selection';
import Button from '../Button/Button';
import { Container, TextArea, ButtonWrapper, buttonStyles } from './ReviewWritingModal.styles';
import { BUTTON_SIZE_TYPE } from '../Button/Button.styles';
import {
  requestGetAllReviewList,
  requestGetSelectedReviewList,
  requestCreateReview,
} from '../../requests';

const ReviewWritingModal = ({ selectedTab, getReviewList, onClickClose }) => {
  const accessToken = useSelector((state) => state.authReducer?.accessToken);
  const { enqueueSnackbar } = useSnackbar();

  const [selectedVaccine, setSelectedVaccine] = useState('모더나');
  const [content, setContent] = useState('');

  const vaccinationList = Object.values(VACCINATION);

  const createReview = async () => {
    const vaccinationType =
      Object.keys(VACCINATION)[Object.values(VACCINATION).indexOf(selectedVaccine)];

    const data = { content, vaccinationType };

    try {
      const response = await requestCreateReview(accessToken, data);

      if (!response.ok) {
        throw new Error(response);
      }

      onClickClose();
      enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_REVIEW);

      if (selectedTab === '전체') {
        getReviewList(requestGetAllReviewList);
      } else {
        const selectedVaccination =
          Object.keys(VACCINATION)[Object.values(VACCINATION).indexOf(selectedTab)];

        getReviewList(() => requestGetSelectedReviewList(selectedVaccination));
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Modal showCloseButton={true} onClickClose={onClickClose}>
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
  selectedTab: PropTypes.string.isRequired,
  getReviewList: PropTypes.func.isRequired,
  onClickClose: PropTypes.func.isRequired,
};

export default ReviewWritingModal;
