import { VACCINE_COLOR, COLOR } from '../../constants';
import Label from './Label';

export default {
  title: 'Label',
  component: Label,
};

const Template = (args) => <Label {...args} />;

export const Small = Template.bind({});
Small.args = {
  children: '모더나',
  sizeType: 'SMALL',
  backgroundColor: VACCINE_COLOR.MODERNA,
  fontColor: COLOR.WHITE,
};

export const Medium = Template.bind({});
Medium.args = {
  children: '모더나',
  sizeType: 'MEDIUM',
  backgroundColor: VACCINE_COLOR.MODERNA,
  fontColor: COLOR.WHITE,
};

export const Large = Template.bind({});
Large.args = {
  children: '모더나',
  sizeType: 'LARGE',
  backgroundColor: VACCINE_COLOR.MODERNA,
  fontColor: COLOR.WHITE,
};
