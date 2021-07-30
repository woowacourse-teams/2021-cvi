import SideBarMobile from './SideBarMobile';

export default {
  title: 'SideBarMobile',
  component: SideBarMobile,
};

const Template = (args) => <SideBarMobile {...args} />;

export const Default = Template.bind({});

Default.args = {};
