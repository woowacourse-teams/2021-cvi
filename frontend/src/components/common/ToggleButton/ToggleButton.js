import PropTypes from 'prop-types';
import React from 'react';
import { Container, DialogButton } from './ToggleButton.styles';

const ToggleButton = ({ selectionList, selected, toggleSelected }) => {
  return (
    <Container onClick={toggleSelected}>
      <DialogButton selected={selected}>
        {selected ? selectionList[0] : selectionList[1]}
      </DialogButton>
    </Container>
  );
};

ToggleButton.propTypes = {
  selected: PropTypes.array.isRequired,
  selectionList: PropTypes.bool.isRequired,
  toggleSelected: PropTypes.func.isRequired,
};

export default ToggleButton;
