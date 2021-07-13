import React from 'react';
import PropTypes from 'prop-types';
import { Container, buttonSelectedStyles } from './Selection.styles';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../Button/Button.styles';
import { PALETTE } from '../../constants';

const Selection = ({ selectionList, selectedItem, setSelectedItem }) => {
  return (
    <Container>
      {selectionList.map((selectionItem) => (
        <Button
          key={selectionItem}
          backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
          sizeType={BUTTON_SIZE_TYPE.LARGE}
          color={PALETTE.NAVY300}
          isSelected={selectedItem === selectionItem}
          selectedStyles={buttonSelectedStyles[selectionItem]}
          type="button"
          onClick={() => setSelectedItem(selectionItem)}
        >
          {selectionItem}
        </Button>
      ))}
    </Container>
  );
};

Selection.propTypes = {
  selectionList: PropTypes.array.isRequired,
  selectedItem: PropTypes.string.isRequired,
  setSelectedItem: PropTypes.func.isRequired,
};

Selection.defaultProps = {};

export default Selection;
