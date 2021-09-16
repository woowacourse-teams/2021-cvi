import lodable from '@loadable/component';

const HomePage = lodable(() => import('./HomePage/HomePage'));
const ReviewPage = lodable(() => import('./ReviewPage/ReviewPage'));
const ReviewDetailPage = lodable(() => import('./ReviewDetailPage/ReviewDetailPage'));
const ReviewEditPage = lodable(() => import('./ReviewEditPage/ReviewEditPage'));
const LoginPage = lodable(() => import('./LoginPage/LoginPage'));
const SignupPage = lodable(() => import('./SignupPage/SignupPage'));
const OAuthPage = lodable(() => import('./OAuthPage/OAuthPage'));
const MyPage = lodable(() => import('./MyPage/MyPage'));
const MyPageAccount = lodable(() => import('./MyPageAccount/MyPageAccount'));
const MyPageReview = lodable(() => import('./MyPageReview/MyPageReview'));
const MyPageCommentReview = lodable(() => import('./MyPageCommentReview/MyPageCommentReview'));
const MyPageLikeReview = lodable(() => import('./MyPageLikeReview/MyPageLikeReview'));
const StatePage = lodable(() => import('./StatePage/StatePage'));

export {
  HomePage,
  ReviewPage,
  ReviewDetailPage,
  ReviewEditPage,
  LoginPage,
  SignupPage,
  OAuthPage,
  MyPage,
  MyPageAccount,
  MyPageReview,
  MyPageCommentReview,
  MyPageLikeReview,
  StatePage,
};
