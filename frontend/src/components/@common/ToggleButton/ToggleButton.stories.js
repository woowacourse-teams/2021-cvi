import ToggleButton from './ToggleButton';

export default {
  title: 'ToggleButton',
  component: ToggleButton,
};

const Template = (args) => <ToggleButton {...args} />;

export const Default = Template.bind({});
Default.args = {
  selectionList: ['막대', '지도'],
  selected: false,
};
