import PropTypes from 'prop-types';
import {
  Container,
  buttonStyles,
  selectedButtonStyles,
  SELECTED_TAB_STYLE_TYPE,
} from './Tabs.styles';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../Button/Button.styles';
import { PALETTE } from '../../../constants';

const Tabs = ({ tabList, selectedTab, selectedTabStyleType, setSelectedTab }) => (
  <Container>
    {tabList.map((tab) => (
      <Button
        key={tab}
        sizeType={BUTTON_SIZE_TYPE.LARGE}
        backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
        color={PALETTE.NAVY300}
        isSelected={selectedTab === tab}
        selectedStyles={selectedButtonStyles[selectedTabStyleType]}
        styles={buttonStyles}
        type="button"
        onClick={() => setSelectedTab(tab)}
      >
        {tab}
      </Button>
    ))}
  </Container>
);

//  ⦁
Tabs.propTypes = {
  tabList: PropTypes.array.isRequired,
  selectedTab: PropTypes.string.isRequired,
  setSelectedTab: PropTypes.func.isRequired,
  selectedTabStyleType: PropTypes.string,
};

Tabs.defaultProps = {
  selectedTabStyleType: SELECTED_TAB_STYLE_TYPE.UNDERLINE,
};

export default Tabs;
