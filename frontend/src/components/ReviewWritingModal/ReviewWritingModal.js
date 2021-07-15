import { useState } from 'react';
import PropTypes from 'prop-types';
import { VACCINATION } from '../../constants';
import Modal from '../Modal/Modal';
import Selection from '../Selection/Selection';
import Button from '../Button/Button';
import { Container, TextArea, ButtonWrapper, buttonStyles } from './ReviewWritingModal.styles';
import { BUTTON_SIZE_TYPE } from '../Button/Button.styles';

const ReviewWritingModal = ({ onClickClose }) => {
  const vaccinationList = Object.keys(VACCINATION);

  const [selectedVaccine, setSelectedVaccine] = useState('모더나');

  return (
    <Modal showCloseButton={true} onClickClose={onClickClose}>
      <Container>
        <Selection
          selectionList={vaccinationList}
          selectedItem={selectedVaccine}
          setSelectedItem={setSelectedVaccine}
        />
        <TextArea width="100%" />
        <ButtonWrapper>
          <Button sizeType={BUTTON_SIZE_TYPE.LARGE} styles={buttonStyles}>
            제출하기
          </Button>
        </ButtonWrapper>
      </Container>
    </Modal>
  );
};

ReviewWritingModal.propTypes = {
  onClickClose: PropTypes.func.isRequired,
};

export default ReviewWritingModal;
