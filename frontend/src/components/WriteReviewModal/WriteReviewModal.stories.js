import WriteReviewModal from './WriteReviewModal';

export default {
  title: 'WriteReviewModal',
  component: WriteReviewModal,
};

const Template = (args) => <WriteReviewModal {...args} />;

export const Default = Template.bind({});
Default.args = {
  children: <div>모달모달</div>,
  onClickClose: () => {
    alert('close Modal');
  },
};
