import { configure, addDecorator } from '@storybook/react';
import * as React from 'react';
import { MemoryRouter } from 'react-router-dom';
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
    <MemoryRouter>{style()}</MemoryRouter>
  </>
));

configure(require.context('../src', true, /\.stories\.js?$/), module);
