import ReviewWritingModal from './ReviewWritingModal';

export default {
  title: 'ReviewWritingModal',
  component: ReviewWritingModal,
};

const Template = (args) => <ReviewWritingModal {...args} />;

export const Default = Template.bind({});
Default.args = {
  children: <div>모달모달</div>,
  onClickClose: () => {
    alert('close Modal');
  },
};
