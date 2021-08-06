import PropTypes from 'prop-types';
import { Container, SelectedFilter, MenuList } from './Dropdown.styles';

const Dropdown = ({ isDropdownVisible, menuList, selectedFilter, onClick }) => {
  return (
    <Container onClick={onClick}>
      <SelectedFilter>{selectedFilter}</SelectedFilter>
      <MenuList isDropdownVisible={isDropdownVisible}>
        {menuList.map((item) => (
          <li key={item}>{item}</li>
        ))}
      </MenuList>
    </Container>
  );
};

Dropdown.propTypes = {
  isDropdownVisible: PropTypes.bool,
  menuList: PropTypes.List,
  selectedFilter: PropTypes.string,
  onClick: PropTypes.func,
};

Dropdown.defaultProps = {
  isDropdownVisible: false,
  menuList: [],
  selectedFilter: '',
  onClick: () => {},
};

export default Dropdown;
