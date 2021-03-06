import PALETTE from './palette';

const VACCINATION_COLOR = {
  ASTRAZENECA: PALETTE.GRAY100,
  MODERNA: PALETTE.RED500,
  PFIZER: PALETTE.PURPLE300,
  JANSSEN: PALETTE.BLUE500,
};

const FONT_COLOR = {
  BLACK: PALETTE.GRAY900,
  WHITE: PALETTE.WHITE,
  BLUE_GRAY: PALETTE.NAVY300,
  GRAY: PALETTE.GRAY700,
  LIGHT_GRAY: PALETTE.GRAY300,
  GREEN: PALETTE.GREEN500,
};

const THEME_COLOR = {
  PRIMARY: PALETTE.BLUE300,
  WHITE: PALETTE.WHITE,
  BACKGROUND: PALETTE.GRAY50,
  PLACEHOLDER: PALETTE.GRAY200,
  FULL_BLACK: PALETTE.BLACK,
};

export { VACCINATION_COLOR, FONT_COLOR, THEME_COLOR };
