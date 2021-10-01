import PropTypes from 'prop-types';
import { CloseIcon } from '../../../assets/icons';
import { FONT_COLOR } from '../../../constants';
import { Button } from '../../@common';
import { BUTTON_BACKGROUND_TYPE } from '../../@common/Button/Button.styles';
import Tabs from '../../@common/Tabs/Tabs';
import { SELECTED_TAB_STYLE_TYPE } from '../../@common/Tabs/Tabs.styles';
import { Container, Type, closeOptionListButtonStyles } from './ReviewFilterList.styles';

const ReviewFilterList = ({
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
      <Type>백신 종류</Type>
      <Tabs
        tabList={vaccineList}
        selectedTab={selectedVaccination}
        selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
        setSelectedTab={setSelectedVaccination}
      />
      <Type>필터</Type>
      <Tabs
        tabList={filterList}
        selectedTab={selectedFilter}
        selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
        setSelectedTab={setSelectedFilter}
      />
      <Type>정렬</Type>
      <Tabs
        tabList={sortList}
        selectedTab={selectedSort}
        selectedTabStyleType={SELECTED_TAB_STYLE_TYPE.LEFT_CIRCLE}
        setSelectedTab={setSelectedSort}
      />
      <Button
        backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
        styles={closeOptionListButtonStyles}
        aria-label="close-button"
        onClick={hideOptionList}
      >
        <CloseIcon height="24" width="24" stroke={FONT_COLOR.BLACK} />
      </Button>
    </Container>
  );
};

ReviewFilterList.propTypes = {
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

export default ReviewFilterList;
