import { SnackbarProvider } from 'notistack';
import GlobalStyles from './GlobalStyles';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';
import { PATH } from './constants';
import {
  HomePage,
  ReviewPage,
  ReviewDetailPage,
  LoginPage,
  SignupPage,
  ReviewEditPage,
  OAuthPage,
  MyPage,
  MyPageAccount,
  MyPageShotVerification,
  MyPageReview,
  MyPageCommentReview,
  MyPageLikeReview,
} from './pages';
import { BaseLayout } from './components/common';

const App = () => {
  return (
    <>
      <SnackbarProvider
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        maxSnack={3}
      >
        <GlobalStyles />
        <BrowserRouter>
          <BaseLayout>
            <Switch>
              <Route exact path={PATH.HOME} component={HomePage} />
              <Route exact path={PATH.REVIEW} component={ReviewPage} />
              <Route exact path={`${PATH.REVIEW}/:id`} component={ReviewDetailPage} />
              <Route exact path={`${PATH.REVIEW}/:id/edit`} component={ReviewEditPage} />
              <Route exact path={PATH.LOGIN} component={LoginPage} />
              <Route exact path={PATH.SIGNUP} component={SignupPage} />
              <Route exact path={PATH.MY_PAGE} component={MyPage} />
              <Route
                exact
                path={PATH.MY_PAGE_ACCOUNT}
                render={() => (
                  <MyPage>
                    <MyPageAccount />
                  </MyPage>
                )}
              />
              <Route
                exact
                path={PATH.MY_PAGE_SHOT_VERIFICATION}
                render={() => (
                  <MyPage>
                    <MyPageShotVerification />
                  </MyPage>
                )}
              />
              <Route
                exact
                path={PATH.MY_PAGE_REVIEW}
                render={() => (
                  <MyPage>
                    <MyPageReview />
                  </MyPage>
                )}
              />
              <Route
                exact
                path={PATH.MY_PAGE_COMMENT_REVIEW}
                render={() => (
                  <MyPage>
                    <MyPageCommentReview />
                  </MyPage>
                )}
              />
              <Route
                exact
                path={PATH.MY_PAGE_LIKE_REVIEW}
                render={() => (
                  <MyPage>
                    <MyPageLikeReview />
                  </MyPage>
                )}
              />
              <Route exact path={PATH.OAUTH_KAKAO} component={OAuthPage} />
              <Route exact path={PATH.OAUTH_NAVER} component={OAuthPage} />
              <Redirect to={PATH.HOME} />
            </Switch>
          </BaseLayout>
        </BrowserRouter>
      </SnackbarProvider>
    </>
  );
};

export default App;
