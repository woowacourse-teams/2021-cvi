import { useState } from 'react';
import PropTypes from 'prop-types';
import { VACCINE } from '../../constants';
import Modal from '../Modal/Modal';
import Selection from '../Selection/Selection';
import Button from '../Button/Button';
import { Container, TextArea, ButtonWrapper } from './WriteReviewModal.styles';
import { BUTTON_SIZE_TYPE } from '../Button/Button.styles';

const WriteReviewModal = ({ onClickClose }) => {
  const selectionList = Object.keys(VACCINE);

  const [selectedVaccine, setSelectedVaccine] = useState('모더나');

  return (
    <Modal showCloseButton={true} onClickClose={onClickClose}>
      <Container>
        <Selection
          selectionList={selectionList}
          selectedItem={selectedVaccine}
          setSelectedItem={setSelectedVaccine}
        />
        <TextArea width="100%" />
        <ButtonWrapper>
          <Button sizeType={BUTTON_SIZE_TYPE.LARGE}>제출하기</Button>
        </ButtonWrapper>
      </Container>
    </Modal>
  );
};

WriteReviewModal.propTypes = {
  onClickClose: PropTypes.func.isRequired,
};

export default WriteReviewModal;
