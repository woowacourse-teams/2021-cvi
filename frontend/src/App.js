import React from 'react';
import GlobalStyles from './GlobalStyles';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { PATH } from './constants';
import { HomePage, ReviewPage } from './pages';
import BaseLayout from './components/BaseLayout/BaseLayout';
import SideBar from './components/SideBar/SideBar';

const App = () => {
  return (
    <>
      <GlobalStyles />
      <BrowserRouter>
        <BaseLayout>
          <SideBar />
          <Switch>
            <Route exact path={PATH.HOME} component={HomePage} />
            <Route exact path={PATH.REVIEW} component={ReviewPage} />
          </Switch>
        </BaseLayout>
      </BrowserRouter>
    </>
  );
};

export default App;
