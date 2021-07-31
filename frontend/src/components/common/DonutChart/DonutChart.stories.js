import DonutChart from './DonutChart';

export default {
  title: 'DonutChart',
  component: DonutChart,
};

const Template = (args) => <DonutChart {...args} />;

export const Default = Template.bind({});
Default.args = {
  target: 37,
};
