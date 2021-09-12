import lodable from '@loadable/component';

const Preview = lodable(() => import('./Preview/Preview'));
const PreviewItem = lodable(() => import('./PreviewItem/PreviewItem'));
const PreviewList = lodable(() => import('./PreviewList/PreviewList'));
const ReviewItem = lodable(() => import('./ReviewItem/ReviewItem'));
const ReviewWritingModal = lodable(() => import('./ReviewWritingModal/ReviewWritingModal'));
const VaccinationState = lodable(() => import('./VaccinationState/VaccinationState'));
const VaccinationSchedule = lodable(() => import('./VaccinationSchedule/VaccinationSchedule'));
const Comment = lodable(() => import('./Comment/Comment'));
const CommentForm = lodable(() => import('./CommentForm/CommentForm'));
const CommentItem = lodable(() => import('./CommentItem/CommentItem'));
const RegionalStateChart = lodable(() => import('./RegionalStateChart/RegionalStateChart'));
const RegionalStateTable = lodable(() => import('./RegionalStateTable/RegionalStateTable'));
const ReviewFilterList = lodable(() => import('./ReviewFilterList/ReviewFilterList'));
const ImageModal = lodable(() => import('./ImageModal/ImageModal'));
const ReviewImage = lodable(() => import('./ReviewImage/ReviewImage'));

export {
  Preview,
  PreviewItem,
  PreviewList,
  ReviewItem,
  ReviewWritingModal,
  VaccinationState,
  VaccinationSchedule,
  Comment,
  CommentForm,
  CommentItem,
  RegionalStateChart,
  RegionalStateTable,
  ReviewFilterList,
  ImageModal,
  ReviewImage,
};
