import { VACCINATION_COLOR, PALETTE } from '../../../constants';
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
  backgroundColor: VACCINATION_COLOR.MODERNA,
  fontColor: PALETTE.WHITE,
};

export const Medium = Template.bind({});
Medium.args = {
  children: '모더나',
  sizeType: 'MEDIUM',
  backgroundColor: VACCINATION_COLOR.MODERNA,
  fontColor: PALETTE.WHITE,
};

export const Large = Template.bind({});
Large.args = {
  children: '모더나',
  sizeType: 'LARGE',
  backgroundColor: VACCINATION_COLOR.MODERNA,
  fontColor: PALETTE.WHITE,
};
