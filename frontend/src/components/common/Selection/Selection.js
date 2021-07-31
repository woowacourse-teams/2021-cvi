import PropTypes from 'prop-types';
import {
  Container,
  buttonSelectedStyles,
  defaultButtonStyles,
  buttonStyles,
} from './Selection.styles';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../Button/Button.styles';
import { PALETTE } from '../../../constants';

const Selection = ({ selectionList, selectedItem, setSelectedItem }) => (
  <Container>
    {selectionList.map((selectionItem) => (
      <Button
        key={selectionItem}
        backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
        sizeType={BUTTON_SIZE_TYPE.LARGE}
        color={PALETTE.NAVY300}
        isSelected={selectedItem === selectionItem}
        selectedStyles={buttonSelectedStyles[selectionItem] || defaultButtonStyles}
        type="button"
        styles={buttonStyles}
        onClick={() => setSelectedItem(selectionItem)}
      >
        {selectionItem}
      </Button>
    ))}
  </Container>
);

Selection.propTypes = {
  selectionList: PropTypes.array.isRequired,
  selectedItem: PropTypes.string.isRequired,
  setSelectedItem: PropTypes.func.isRequired,
};

Selection.defaultProps = {};

export default Selection;
