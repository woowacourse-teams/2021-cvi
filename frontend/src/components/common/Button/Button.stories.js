import Button from './Button';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from './Button.styles';

export default {
  title: 'Button',
  component: Button,
};

const Template = (args) => <Button {...args} />;

export const Filled = Template.bind({});
Filled.args = {
  children: '후기 작성',
  backgroundType: BUTTON_BACKGROUND_TYPE.FILLED,
  sizeType: BUTTON_SIZE_TYPE.MEDIUM,
};

export const Outline = Template.bind({});
Outline.args = {
  children: '후기 작성',
  backgroundType: BUTTON_BACKGROUND_TYPE.OUTLINE,
  sizeType: BUTTON_SIZE_TYPE.MEDIUM,
};

export const Text = Template.bind({});
Text.args = {
  children: '후기 작성',
  backgroundType: BUTTON_BACKGROUND_TYPE.TEXT,
  sizeType: BUTTON_SIZE_TYPE.MEDIUM,
};

export const Icon = Template.bind({});
Icon.args = {
  children: (
    <>
      <div>더 보기</div>
      <div>Icon</div>
    </>
  ),
  backgroundType: BUTTON_BACKGROUND_TYPE.TEXT,
  sizeType: BUTTON_SIZE_TYPE.MEDIUM,
  withIcon: true,
};
