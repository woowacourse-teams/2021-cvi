import Frame from './Frame';

export default {
  title: 'Frame',
  component: Frame,
};

const Template = (args) => <Frame {...args} />;

export const Default = Template.bind({});

Default.args = {
  children: 'hello',
  backgroundColor: '#FFFFFF',
  width: '100rem',
  height: '50rem',
  styles: { padding: '5.0rem' },
};
