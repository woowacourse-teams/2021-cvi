import PropTypes from 'prop-types';
import { CloseIcon } from '../../../assets/icons';
import { FONT_COLOR } from '../../../constants';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';
import Tabs from '../Tabs/Tabs';
import { SELECTED_TAB_STYLE_TYPE } from '../Tabs/Tabs.styles';
import { Container, closeOptionListButtonStyles } from './OptionList.styles';

const OptionList = ({
  vaccineList,
  selectedVaccination,
  setSelectedVaccination,
  filterList,
  selectedFilter,
  setSelectedFilter,
  sortList,
  selectedSort,
  setSelectedSort,
  hideOptionList,
}) => {
  return (
    <Container>
      <div>백신 종류</div>
      <Tabs
        tabList={vaccineList}
        selectedTab={selectedVaccination}
        selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
        setSelectedTab={setSelectedVaccination}
      />
      <div>필터</div>
      <Tabs
        tabList={filterList}
        selectedTab={selectedFilter}
        selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
        setSelectedTab={setSelectedFilter}
      />
      <div>정렬</div>
      <Tabs
        tabList={sortList}
        selectedTab={selectedSort}
        selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
        setSelectedTab={setSelectedSort}
      />
      <Button
        backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
        styles={closeOptionListButtonStyles}
        onClick={hideOptionList}
      >
        <CloseIcon height="24" width="24" stroke={FONT_COLOR.BLACK} />
      </Button>
    </Container>
  );
};

OptionList.propTypes = {
  filterList: PropTypes.array.isRequired,
  hideOptionList: PropTypes.func.isRequired,
  selectedFilter: PropTypes.string.isRequired,
  selectedSort: PropTypes.string.isRequired,
  selectedVaccination: PropTypes.string.isRequired,
  setSelectedFilter: PropTypes.func.isRequired,
  setSelectedSort: PropTypes.func.isRequired,
  setSelectedVaccination: PropTypes.func.isRequired,
  sortList: PropTypes.array.isRequired,
  vaccineList: PropTypes.array.isRequired,
};

export default OptionList;
