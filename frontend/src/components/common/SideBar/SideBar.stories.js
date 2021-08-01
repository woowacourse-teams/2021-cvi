import SideBar from './SideBar';

export default {
  title: 'SideBar',
  component: SideBar,
};

const Template = (args) => <SideBar {...args} />;

export const Default = Template.bind({});

Default.args = {};
