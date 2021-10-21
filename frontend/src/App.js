import GlobalStyles from './GlobalStyles';
import { BrowserRouter, Switch, Redirect } from 'react-router-dom';
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
  MyPageReview,
  MyPageCommentReview,
  MyPageLikeReview,
  StatePage,
} from './pages';
import { BaseLayout, PrivateRoute, PublicRoute } from './components/@common';
import { useSnackbar } from './hooks';

const App = () => {
  const { isSnackbarOpen, Snackbar } = useSnackbar();

  return (
    <>
      <GlobalStyles />
      <BrowserRouter>
        <BaseLayout>
          <Switch>
            <PublicRoute exact path={PATH.HOME} component={HomePage} />
            <PublicRoute exact path={PATH.REVIEW} component={ReviewPage} />
            <PublicRoute exact path={`${PATH.REVIEW}/:id`} component={ReviewDetailPage} />
            <PublicRoute
              exact
              path={`${PATH.REVIEW}/:id/edit`}
              component={() => <ReviewEditPage />}
            />
            <PublicRoute restricted exact path={PATH.LOGIN} component={LoginPage} />
            <PublicRoute restricted exact path={PATH.SIGNUP} component={SignupPage} />
            <PrivateRoute
              exact
              path={PATH.MY_PAGE_ACCOUNT}
              render={() => (
                <MyPage>
                  <MyPageAccount />
                </MyPage>
              )}
            />
            <PrivateRoute
              exact
              path={PATH.MY_PAGE_REVIEW}
              render={() => (
                <MyPage>
                  <MyPageReview />
                </MyPage>
              )}
            />
            <PrivateRoute
              exact
              path={PATH.MY_PAGE_COMMENT_REVIEW}
              render={() => (
                <MyPage>
                  <MyPageCommentReview />
                </MyPage>
              )}
            />
            <PrivateRoute
              exact
              path={PATH.MY_PAGE_LIKE_REVIEW}
              render={() => (
                <MyPage>
                  <MyPageLikeReview />
                </MyPage>
              )}
            />
            <PublicRoute restricted exact path={PATH.OAUTH_KAKAO} component={OAuthPage} />
            <PublicRoute restricted exact path={PATH.OAUTH_NAVER} component={OAuthPage} />
            <PublicRoute exact path={PATH.STATE} component={StatePage} />
            <Redirect to={PATH.HOME} />
          </Switch>
        </BaseLayout>
      </BrowserRouter>
      {isSnackbarOpen && <Snackbar />}
    </>
  );
};

export default App;
