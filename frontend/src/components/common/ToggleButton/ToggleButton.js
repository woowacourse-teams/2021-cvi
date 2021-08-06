import PropTypes from 'prop-types';
import { MapIcon, BarChartIcon } from '../../../assets/icons';
import { Container, ToggleItem, DialogButton } from './ToggleButton.styles';

const ToggleButton = ({ selectionList, selected, toggleSelected }) => (
  <Container onClick={toggleSelected}>
    <ToggleItem>
      <MapIcon height="14" width="14" />
      {selectionList[1]}
    </ToggleItem>
    <ToggleItem>
      <BarChartIcon height="16" width="16" />
      {selectionList[0]}
    </ToggleItem>
    <DialogButton selected={selected}>
      {selected ? (
        <ToggleItem isSelected={true}>
          <BarChartIcon height="16" width="16" />
          {selectionList[0]}
        </ToggleItem>
      ) : (
        <ToggleItem isSelected={true}>
          <MapIcon height="14" width="14" />
          {selectionList[1]}
        </ToggleItem>
      )}
    </DialogButton>
  </Container>
);

ToggleButton.propTypes = {
  selected: PropTypes.array.isRequired,
  selectionList: PropTypes.bool.isRequired,
  toggleSelected: PropTypes.func.isRequired,
};

export default ToggleButton;
