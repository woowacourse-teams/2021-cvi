import React from 'react';
import PropTypes from 'prop-types';
import { Container, buttonStyles, selectedButtonStyles } from './Tabs.styles';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';
import { PALETTE } from '../../constants';

const Tabs = ({ tabList, selectedTab, setSelectedTab }) => {
  return (
    <Container>
      {tabList.map((tab) => (
        <Button
          key={tab}
          backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
          color={PALETTE.NAVY300}
          isSelected={selectedTab === tab}
          selectedStyles={selectedButtonStyles}
          styles={buttonStyles}
          type="button"
          onClick={() => setSelectedTab(tab)}
        >
          {tab}
        </Button>
      ))}
    </Container>
  );
};

Tabs.propTypes = {
  tabList: PropTypes.array.isRequired,
  selectedTab: PropTypes.string.isRequired,
  setSelectedTab: PropTypes.func.isRequired,
};

Tabs.defaultProps = {};

export default Tabs;
