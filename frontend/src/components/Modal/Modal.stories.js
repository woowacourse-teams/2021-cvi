import Modal from './Modal';

export default {
  title: 'Modal',
  component: Modal,
};

const Template = (args) => <Modal {...args} />;

export const Default = Template.bind({});
Default.args = {
  children: <div>모달모달</div>,
  onClickClose: () => {
    alert('close Modal');
  },
};

export const WithCloseButton = Template.bind({});
WithCloseButton.args = {
  children: <div>모달모달</div>,
  showCloseButton: true,
  onClickClose: () => {
    alert('close Modal');
  },
};
