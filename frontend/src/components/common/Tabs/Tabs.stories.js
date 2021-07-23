import Tabs from './Tabs';

export default {
  title: 'Tabs',
  component: Tabs,
};

const Template = (args) => <Tabs {...args} />;

export const Default = Template.bind({});
Default.args = {
  tabList: ['전체', '모더나', '화이자', '아스트라제네카', '얀센'],
};
