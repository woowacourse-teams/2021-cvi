import { FILTER_TYPE, FONT_COLOR } from '../../../constants';
import { Title, Container, ButtonContainer, buttonStyles } from './Preview.styles';
import PropTypes from 'prop-types';
import PreviewList from '../PreviewList/PreviewList';
import { RightArrowIcon } from '../../../assets/icons';
import { Button, Frame } from '../../@common';
import { BUTTON_BACKGROUND_TYPE } from '../../@common/Button/Button.styles';
import { useMovePage } from '../../../hooks';

const Preview = ({ title, reviewType }) => {
  const { goReviewPage } = useMovePage();

  const goReviewPageWithFilter = () => {
    if (reviewType === FILTER_TYPE.CREATED_AT) {
      goReviewPage();
    } else if (reviewType === FILTER_TYPE.LIKE_COUNT) {
      goReviewPage({
        selectedFilter: FILTER_TYPE.LIKE_COUNT,
      });
    }
  };

  return (
    <div>
      <ButtonContainer>
        {title && <Title>{title}</Title>}
        <Button
          backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
          color={FONT_COLOR.BLUE_GRAY}
          withIcon={true}
          styles={buttonStyles}
          onClick={goReviewPageWithFilter}
        >
          <div>더보기</div>
          <RightArrowIcon width="18" height="18" stroke={FONT_COLOR.BLUE_GRAY} />
        </Button>
      </ButtonContainer>

      <Frame width="100%" showShadow={true}>
        <Container>
          <PreviewList reviewType={reviewType} />
        </Container>
      </Frame>
    </div>
  );
};

Preview.propTypes = {
  title: PropTypes.string,
  reviewType: PropTypes.string,
};

Preview.defaultProps = {
  title: '',
  reviewType: FILTER_TYPE.CREATED_AT,
};

export default Preview;
