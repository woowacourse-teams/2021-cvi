import { configure, addDecorator } from '@storybook/react';
import * as React from 'react';
import GlobalStyles from '../src/GlobalStyles';

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
  layout: 'centered',
};

addDecorator((style) => (
  <>
    <GlobalStyles />
    {style()}
  </>
));

configure(require.context('../src', true, /\.stories\.js?$/), module);
