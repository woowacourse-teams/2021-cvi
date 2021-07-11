import React from 'react';
import PropTypes from 'prop-types';
import { Container, buttonSelectedStyles, buttonStyles } from './Selection.styles';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../Button/Button.styles';
import { PALETTE } from '../../constants';

const Selection = ({ selectionList }) => {
  return (
    <Container>
      {selectionList.map((selectionItem) => (
        <Button
          key={selectionItem}
          backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
          sizeType={BUTTON_SIZE_TYPE.LARGE}
          color={PALETTE.NAVY300}
          isSelected={'아스트라제네카' === selectionItem}
          selectedStyles={buttonSelectedStyles[selectionItem]}
          styles={buttonStyles}
        >
          {selectionItem}
        </Button>
      ))}
    </Container>
  );
};

Selection.propTypes = {
  selectionList: PropTypes.array.isRequired,
};

Selection.defaultProps = {};

export default Selection;
