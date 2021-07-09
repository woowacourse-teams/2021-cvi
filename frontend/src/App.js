import React from 'react';
import GlobalStyles from './GlobalStyles';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { PATH } from './constants';
import { HomePage, ReviewPage } from './pages';

const App = () => {
  return (
    <>
      <GlobalStyles />
      <BrowserRouter>
        <Switch>
          <Route exact path={PATH.HOME} component={HomePage} />
          <Route exact path={PATH.REVIEW} component={ReviewPage} />
        </Switch>
      </BrowserRouter>
    </>
  );
};

export default App;
