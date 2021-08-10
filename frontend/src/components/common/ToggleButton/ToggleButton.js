import PropTypes from 'prop-types';
import { MapIcon, BarChartIcon } from '../../../assets/icons';
import { Container, ToggleItem, DialogButton } from './ToggleButton.styles';

const ToggleButton = ({ selectionList, selected, toggleSelected }) => (
  <Container selected={selected} onClick={toggleSelected}>
    <ToggleItem selected={selected}>
      <MapIcon height="14" width="14" />
      {selectionList[1]}
    </ToggleItem>
    <ToggleItem selected={selected}>
      <BarChartIcon height="16" width="16" />
      {selectionList[0]}
    </ToggleItem>
    <DialogButton selected={selected} />
  </Container>
);

ToggleButton.propTypes = {
  selected: PropTypes.bool.isRequired,
  selectionList: PropTypes.array.isRequired,
  toggleSelected: PropTypes.func.isRequired,
};

export default ToggleButton;
