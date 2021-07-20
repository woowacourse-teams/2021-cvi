import Selection from './Selection';

export default {
  title: 'Selection',
  component: Selection,
};

const Template = (args) => <Selection {...args} />;

export const Default = Template.bind({});
Default.args = {
  selectionList: ['모더나', '화이자', '아스트라제네카', '얀센'],
};
