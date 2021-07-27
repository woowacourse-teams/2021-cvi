import { FONT_COLOR, PATH } from '../../constants';
import { Title, Container, ButtonContainer } from './Preview.styles';
import PropTypes from 'prop-types';
import PreviewList from '../PreviewList/PreviewList';
import { useHistory } from 'react-router-dom';
import { RightArrowIcon } from '../../assets/icons';
import { Button, Frame } from '../common';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';

const Preview = ({ title }) => {
  const history = useHistory();

  const goReviewPage = () => {
    history.push(`${PATH.REVIEW}`);
  };

  return (
    <div>
      {title && <Title>{title}</Title>}
      <Frame width="100%" showShadow={true}>
        <Container>
          <ButtonContainer>
            <Button
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              color={FONT_COLOR.BLUE_GRAY}
              withIcon={true}
              onClick={goReviewPage}
            >
              <div>더보기</div>
              <RightArrowIcon width="18" height="18" stroke={FONT_COLOR.BLUE_GRAY} />
            </Button>
          </ButtonContainer>
          <PreviewList />
        </Container>
      </Frame>
    </div>
  );
};

Preview.propTypes = {
  title: PropTypes.string,
};

Preview.defaultProps = {
  title: '',
};

export default Preview;
